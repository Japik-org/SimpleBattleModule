package com.gvargame.server.modules.simplebattle.packet;

public final class PacketId {
    public static final class Server{
        public static final short WRONG = 0;
        public static final short MSG_ERROR = 1;
        public static final short PLAYER_POS = 2;
    }

    public static final class Client{
        public static final short WRONG = 0;
    }
}
