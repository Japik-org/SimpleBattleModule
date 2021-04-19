package com.gvargame.server.modules.simplebattle.packet;

import com.gvargame.server.modules.simplebattle.MsgErrorCode;
import com.gvargame.server.modules.simplebattle.ProcedureIteratePlayers;
import com.pro100kryto.server.utils.datagram.packets.DataCreator;

public final class PacketCreator {
    private static final ProcedureIteratePlayers iteratePlayers
            = new ProcedureIteratePlayers(3300); // 30 players aprox

    protected static void setHeader(DataCreator creator, short packetId){
        creator.setPosition(PacketHeaderInfo.Server.POS_PACKET_ID);
        creator.write(packetId);
        creator.setPosition(PacketHeaderInfo.Server.POS_BODY);
    }

    public static void msgError(DataCreator creator, MsgErrorCode msgError){
        setHeader(creator, PacketId.Server.MSG_ERROR);
        creator.write(msgError.ordinal());
    }

    public static void playerPositions(DataCreator creator){
        setHeader(creator, PacketId.Server.PLAYER_POS);
        iteratePlayers.writePlayerPositions(creator);
    }

    public static ProcedureIteratePlayers getIteratePlayers() {
        return iteratePlayers;
    }
}