package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.Player;
import com.gvargame.server.modules.simplebattle.packet.PacketCreator;
import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.modules.sender.connection.ISenderModuleConnection;
import com.pro100kryto.server.utils.datagram.packets.DataCreator;
import com.pro100kryto.server.utils.datagram.packets.DataReader;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

import javax.vecmath.Vector3f;

public class ShootVoidPacketProcess extends PacketProcess{


    public ShootVoidPacketProcess(IPacketProcessCallback callback, ILogger logger) {
        super(callback, logger);
    }

    @Override
    public void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable {

        final Vector3f direction = readVector3f(reader);

        final IPacketInProcess newPacket = callback.getPacketPool().getNextPacket();
        try {
            final DataCreator creator = newPacket.getDataCreator();

            PacketCreator.shootVoid(creator, direction);

            final ISenderModuleConnection sender = callback.getSender();
            callback.getPlayersArray().iteratePlayers((p)->{
                newPacket.setEndPoint(player.getEndPoint());
                sender.sendPacket(newPacket);
            });
        } catch (NullPointerException ignored){
        }

        newPacket.recycle();
    }
}
