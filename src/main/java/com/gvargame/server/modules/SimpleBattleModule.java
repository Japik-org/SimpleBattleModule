package com.gvargame.server.modules;

import com.gvargame.server.modules.simplebattle.BattleSimpleProcess;
import com.gvargame.server.modules.simplebattle.PlayersArray;
import com.gvargame.server.modules.simplebattle.ProcedureIteratePlayers;
import com.gvargame.server.modules.simplebattle.Tick;
import com.gvargame.server.modules.simplebattle.packet.PacketCreator;
import com.gvargame.server.modules.simplebattle.packet.PacketId;
import com.gvargame.server.modules.simplebattle.packetprocess2.*;
import com.pro100kryto.server.module.IModuleConnectionSafe;
import com.pro100kryto.server.module.Module;
import com.pro100kryto.server.modules.packetpool.connection.IPacketPoolModuleConnection;
import com.pro100kryto.server.modules.receiverbuffered.connection.IReceiverBufferedModuleConnection;
import com.pro100kryto.server.modules.sender.connection.ISenderModuleConnection;
import com.pro100kryto.server.service.IServiceControl;
import com.pro100kryto.server.utils.ServerUtils.IntCounterLocked;
import com.pro100kryto.server.utils.datagram.objectpool.ObjectPool;
import com.pro100kryto.server.utils.datagram.packetprocess2.IPacketProcess;
import com.pro100kryto.server.utils.datagram.packetprocess2.ProcessorThreadPool;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.jetbrains.annotations.Nullable;

import javax.vecmath.Vector3f;
import java.util.Objects;

public class SimpleBattleModule extends Module implements IPacketProcessCallback {

    private IModuleConnectionSafe<IReceiverBufferedModuleConnection> receiverModuleConnection;
    private IModuleConnectionSafe<IPacketPoolModuleConnection> packetPoolModuleConnection;
    private IModuleConnectionSafe<ISenderModuleConnection> senderModuleConnection;

    private ProcessorThreadPool<BattleSimpleProcess> processor;
    private ObjectPool<BattleSimpleProcess> processesPool;
    private IntObjectHashMap<IPacketProcess> packetIdPacketProcessMap;

    private PlayersArray playersArray;

    private IntObjectHashMap<Tick> stepTickMap;
    private IntCounterLocked tickCounter;


    public SimpleBattleModule(IServiceControl service, String name) {
        super(service, name);
    }

    @Override
    protected void startAction() throws Throwable {
        packetPoolModuleConnection = initModuleConnection(settings.getOrDefault("packetpool-module-name", "packetPool"));
        senderModuleConnection = initModuleConnection(settings.getOrDefault("sender-module-name", "sender"));
        receiverModuleConnection = initModuleConnection(settings.getOrDefault("receiver-module-name", "receiver"));

        final Vector3f[] spawnPoints = new Vector3f[2];
        spawnPoints[0] = new Vector3f(0,10,10);
        spawnPoints[1] = new Vector3f(10,10,10);
        /*
        {
            final String spawnPointsStr = settings.getOrDefault("battle-spawn-points", "[(0, 10, 10),(10, 10, 10)]");
            final String[] match = spawnPointsStr.matches()
            spawnPoints = new Vector3f[]
        }
        */

        packetIdPacketProcessMap = new IntObjectHashMap<>();
        packetIdPacketProcessMap.put(PacketId.Client.WRONG, new WrongPacketPacketProcess());
        packetIdPacketProcessMap.put(PacketId.Client.BODY_POS, new UpdateBodyPositionPacketProcess(this, logger));
        packetIdPacketProcessMap.put(PacketId.Client.GUN_POS, new UpdateGunPositionPacketProcess(this, logger));
        packetIdPacketProcessMap.put(PacketId.Client.SPAWN_REQ, new SpawnRequestPacketProcess(this, logger, spawnPoints));
        packetIdPacketProcessMap.put(PacketId.Client.KILL_SELF, new KillSelfPacketProcess(this, logger));
        packetIdPacketProcessMap.put(PacketId.Client.SHOOT_VOID, new ShootVoidPacketProcess(this, logger));
        packetIdPacketProcessMap.put(PacketId.Client.SHOOT_WALL, new ShootWallPacketProcess(this, logger));
        packetIdPacketProcessMap.put(PacketId.Client.SHOOT_PLAYER, new ShootPlayerPacketProcess(this, logger));


        final int maxProcesses = Integer.parseInt(settings.getOrDefault("max-processes", "256"));
        processesPool = new ObjectPool<BattleSimpleProcess>(maxProcesses) {
            @Override
            protected BattleSimpleProcess createRecycledObject() {
                return new BattleSimpleProcess(processesPool, packetIdPacketProcessMap);
            }
        };
        processesPool.refill();
        processor = new ProcessorThreadPool<>(maxProcesses);

        playersArray = new PlayersArray();

        stepTickMap = new IntObjectHashMap<>(2);
        stepTickMap.put(0, this::tick0);
        stepTickMap.put(1, this::tick1);

        final int counterMaxValue = Integer.parseInt(settings.getOrDefault("", "1"));
        tickCounter = new IntCounterLocked(0, 0, counterMaxValue);
    }

    @Override
    protected void stopAction(boolean force) throws Throwable {
        playersArray.removeAll();
        packetIdPacketProcessMap.clear();
        processesPool.clear();
        stepTickMap.clear();
    }

    @Override
    public void tick() throws Throwable {
        stepTickMap.get(tickCounter.getAndIncrement()/tickCounter.getMaxValue()).tick();
    }

    // main thread
    private void tick0() throws Throwable{
        try {
            final IPacket packet = receiverModuleConnection.getModuleConnection().getNextPacket();
            Objects.requireNonNull(packet);

            try {
                final BattleSimpleProcess process = processesPool.nextAndGet();
                process.setPacket(packet);
                processor.startOrRecycle(process);

            } catch (final Throwable throwable){
                logger.writeException(throwable, "Failed process packet");
            }

            //tickCounter.setValue(0);
        } catch (final NullPointerException ignored){
        }
    }

    // helper thread
    private void tick1() throws Throwable{
        final ProcedureIteratePlayers iteratePlayers = PacketCreator.getIteratePlayers();
        iteratePlayers.startNewIteration();
        try {
            playersArray.iteratePlayers(iteratePlayers);
        } catch (Throwable ignored){}
        iteratePlayers.endIteration();
    }

    // ---------- callback

    @Nullable
    @Override
    public IPacketPoolModuleConnection getPacketPool() {
        return packetPoolModuleConnection.getModuleConnection();
    }

    @Nullable
    @Override
    public ISenderModuleConnection getSender() {
        return senderModuleConnection.getModuleConnection();
    }

    @Override
    public PlayersArray getPlayersArray() {
        return playersArray;
    }
}
