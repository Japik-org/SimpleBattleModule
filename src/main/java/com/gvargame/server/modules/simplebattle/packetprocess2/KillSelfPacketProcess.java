package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.Player;
import com.gvargame.server.modules.simplebattle.packet.PacketCreator;
import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.utils.datagram.packets.*;

public class KillSelfPacketProcess extends PacketProcess{


    public KillSelfPacketProcess(IPacketProcessCallback callback, ILogger logger) {
        super(callback, logger);
    }

    @Override
    public void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable {
        if (player.getHp()<=0) return;
        final IPacketInProcess newPacket = callback.getPacketPool().getNextPacket();
        newPacket.setEndPoint(new EndPoint(packet.getEndPoint()));
        try {
            final DataCreator creator = newPacket.getDataCreator();

            PacketCreator.dead(creator);

            callback.getSender().sendPacketAndRecycle(newPacket);
            return;
        } catch (NullPointerException ignored){
        }
        newPacket.recycle();
    }
}
