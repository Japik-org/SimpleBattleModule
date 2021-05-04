package com.pro100kryto.server.modules.simplebattle.packetprocess2;

import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.modules.sender.connection.ISenderModuleConnection;
import com.pro100kryto.server.modules.simplebattle.Player;
import com.pro100kryto.server.modules.simplebattle.packet.PacketCreator;
import com.pro100kryto.server.utils.datagram.packets.DataCreator;
import com.pro100kryto.server.utils.datagram.packets.DataReader;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

import javax.vecmath.Vector3f;

public class ShootPlayerPacketProcess extends PacketProcess{


    public ShootPlayerPacketProcess(IPacketProcessCallback callback, ILogger logger) {
        super(callback, logger);
    }

    @Override
    public void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable {

        final Vector3f direction = readVector3f(reader);
        final Vector3f intersection = readVector3f(reader);

        final IPacketInProcess newPacket = callback.getPacketPool().getNextPacket();
        try {
            final DataCreator creator = newPacket.getDataCreator();

            PacketCreator.shoot(creator, direction, intersection);

            final ISenderModuleConnection sender = callback.getSender();
            callback.getPlayersArray().iteratePlayers((p)->{
                newPacket.setEndPoint(player.getConnectionInfo().getEndPoint());
                sender.sendPacket(newPacket);
            });
            newPacket.recycle();
        } catch (Throwable throwable){
            newPacket.recycle();
            throw throwable;
        }
    }
}
