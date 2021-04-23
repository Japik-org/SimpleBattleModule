package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.Player;
import com.gvargame.server.modules.simplebattle.packet.PacketCreator;
import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.utils.datagram.packets.DataCreator;
import com.pro100kryto.server.utils.datagram.packets.DataReader;
import com.pro100kryto.server.utils.datagram.packets.IPacket;
import com.pro100kryto.server.utils.datagram.packets.IPacketInProcess;

public class RequestPlayerPositionsPacketProcess extends PacketProcess{


    protected RequestPlayerPositionsPacketProcess(IPacketProcessCallback callback, ILogger logger) {
        super(callback, logger);
    }

    @Override
    public void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable {
        // no read

        // new packet
        final IPacketInProcess newPacket = callback.getPacketPool().getNextPacket();
        newPacket.setEndPoint(packet.getEndPoint());
        try {
            final DataCreator creator = newPacket.getDataCreator();

            PacketCreator.playerPositions(creator);

            callback.getSender().sendPacketAndRecycle(newPacket);
            return;
        } catch (NullPointerException ignored){
        }
        newPacket.recycle();
    }
}
