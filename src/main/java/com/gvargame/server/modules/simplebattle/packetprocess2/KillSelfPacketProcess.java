package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.Player;
import com.gvargame.server.modules.simplebattle.packet.PacketCreator;
import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.modules.sender.connection.ISenderModuleConnection;
import com.pro100kryto.server.utils.datagram.packets.DataCreator;
import com.pro100kryto.server.utils.datagram.packets.DataReader;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

public class KillSelfPacketProcess extends PacketProcess{


    public KillSelfPacketProcess(IPacketProcessCallback callback, ILogger logger) {
        super(callback, logger);
    }

    @Override
    public void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable {
        if (player.getHp()<=0) return;
        final IPacketInProcess newPacket = callback.getPacketPool().getNextPacket();
        try {
            final DataCreator creator = newPacket.getDataCreator();

            PacketCreator.dead(creator, player.getConnId());

            final ISenderModuleConnection sender = callback.getSender();
            callback.getPlayersArray().iteratePlayers((p)->{
                newPacket.setEndPoint(packet.getEndPoint());
                sender.sendPacket(newPacket);
            });
        } catch (NullPointerException ignored){
        }
        newPacket.recycle();
    }
}
