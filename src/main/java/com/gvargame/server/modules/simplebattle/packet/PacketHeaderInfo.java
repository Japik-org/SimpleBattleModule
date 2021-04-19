package com.gvargame.server.modules.simplebattle.packet;

public final class PacketHeaderInfo {
    public static final class Client {
        public static final int POS_PACKET_ID = 0;
        public static final int POS_BODY = 2;
    }

    public static final class Server{
        public static final int POS_PACKET_ID = 0;
        public static final int POS_BODY = 2;
    }
}
