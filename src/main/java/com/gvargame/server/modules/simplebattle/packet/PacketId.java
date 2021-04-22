package com.gvargame.server.modules.simplebattle.packet;

public final class PacketId {
    public static final class Server{
        public static final short WRONG = 0;
        public static final short PONG = 1;
        public static final short MSG_ERROR = 2;
        public static final short PLAYER_POS = 3;
        public static final short SPAWN = 4;
        public static final short DEAD = 5;
    }

    public static final class Client{
        public static final short WRONG = 0;
        public static final short PING = 1;
        public static final short BODY_POS = 2;
        public static final short GUN_POS = 3;
        public static final short SPAWN_REQ = 4;
        public static final short KILL_SELF = 5;
    }
}
