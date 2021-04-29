package com.gvargame.server.modules.simplebattle.packet;

import com.gvargame.server.modules.simplebattle.MsgErrorCode;
import com.gvargame.server.modules.simplebattle.ProcedureIteratePlayers;
import com.pro100kryto.server.utils.datagram.packets.DataCreator;

import javax.vecmath.Vector3f;

public final class PacketCreator {
    private static final ProcedureIteratePlayers iteratePlayers
            = new ProcedureIteratePlayers(3300, 3); // 30 players aprox

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
        creator.write(iteratePlayers.getPlayersCount());
        iteratePlayers.writeCountAndPlayerPositions(creator);
    }

    public static ProcedureIteratePlayers getIteratePlayers() {
        return iteratePlayers;
    }

    public static void confirmRespawn(DataCreator creator, Vector3f spawnPoint){
        setHeader(creator, PacketId.Server.SPAWN);
        creator.write(spawnPoint.x, spawnPoint.y, spawnPoint.z);
    }

    public static void dead(DataCreator creator, int connId){
        setHeader(creator, PacketId.Server.DEAD);
        creator.write(connId);
    }


    public static void shootVoid(DataCreator creator, Vector3f direction) {
        setHeader(creator, PacketId.Server.SHOOT_VOID);
        creator.write(direction.x, direction.y, direction.z);
    }

    public static void shoot(DataCreator creator, Vector3f direction, Vector3f intersection) {
        setHeader(creator, PacketId.Server.SHOOT);
        creator.write(direction.x, direction.y, direction.z);
        creator.write(intersection.x, intersection.y, intersection.z);
    }
}
