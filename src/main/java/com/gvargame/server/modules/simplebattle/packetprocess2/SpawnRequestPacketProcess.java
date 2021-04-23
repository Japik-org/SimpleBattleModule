package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.Player;
import com.gvargame.server.modules.simplebattle.packet.PacketCreator;
import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.utils.datagram.packets.DataCreator;
import com.pro100kryto.server.utils.datagram.packets.DataReader;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

import javax.vecmath.Vector3f;
import java.util.Random;

public class SpawnRequestPacketProcess extends PacketProcess{
    private final Vector3f[] spawnPoints;
    private final int spawnPointsCount;
    private final Random spawnRandom;

    public SpawnRequestPacketProcess(IPacketProcessCallback callback, ILogger logger, Vector3f[] spawnPoints) {
        super(callback, logger);
        this.spawnPoints = spawnPoints;
        spawnPointsCount = spawnPoints.length;
        spawnRandom = new Random();
    }


    @Override
    public void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable {
        if (player.getHp()>0) return;
        final Vector3f spawnPoint = spawnPoints[spawnRandom.nextInt(spawnPointsCount)];
        player.respawn(spawnPoint);

        final IPacketInProcess newPacket = callback.getPacketPool().getNextPacket();
        newPacket.setEndPoint(packet.getEndPoint());
        try {
            final DataCreator creator = newPacket.getDataCreator();

            PacketCreator.confirmRespawn(creator, spawnPoint);

            callback.getSender().sendPacketAndRecycle(newPacket);
            return;
        } catch (NullPointerException ignored){
        }
        newPacket.recycle();
    }
}
