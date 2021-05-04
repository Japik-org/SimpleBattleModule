package com.pro100kryto.server.modules.simplebattle.packet;

public final class PacketId {
    public static final class Server{
        public static final short WRONG = 0;
        public static final short PONG = 1;
        public static final short PING = 2;
        public static final short MSG_ERROR = 3;
        public static final short PLAYER_POS = 4;
        public static final short SPAWN = 5;
        public static final short DEAD = 6;
        public static final short SHOOT_VOID = 7;
        public static final short SHOOT = 8;
        public static final short PLAYERS_LIST = 9;
        public static final short PLAYER_JOINED = 10;
        public static final short PLAYER_LEFT = 11;
    }

    public static final class Client{
        public static final short WRONG = 0;
        public static final short PING = 1;
        public static final short PONG = 2;
        public static final short BODY_POS = 3;
        public static final short GUN_POS = 4;
        public static final short SPAWN_REQ = 8;
        public static final short KILL_SELF = 9;
        public static final short SHOOT_VOID = 7;
        public static final short SHOOT_WALL = 8;
        public static final short SHOOT_PLAYER = 9;
        public static final short PLAYERS_LIST = 10;
    }
}
