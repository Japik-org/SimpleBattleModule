package com.pro100kryto.server.modules.simplebattle.packetprocess2;

import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.modules.simplebattle.Player;
import com.pro100kryto.server.utils.datagram.packets.DataReader;
import com.pro100kryto.server.utils.datagram.packets.IPacket;

import javax.vecmath.Vector3f;

public class UpdateBodyPositionPacketProcess extends PacketProcess{


    public UpdateBodyPositionPacketProcess(IPacketProcessCallback callback, ILogger logger) {
        super(callback, logger);
    }

    @Override
    public void processPacket(IPacket packet, final DataReader reader, Player player) throws Throwable {
        // read
        final Vector3f pos          = readVector3f(reader);
        final Vector3f posSpeed     = readVector3f(reader);
        final Vector3f posAccel     = readVector3f(reader);
        final Vector3f angle        = readVector3f(reader);
        final Vector3f angleSpeed   = readVector3f(reader);
        final Vector3f angleAccel   = readVector3f(reader);

        // save
        player.setBodyPos(pos);
        player.setBodySpeed(posSpeed);
        player.setBodyAccel(posAccel);
        player.setBodyAngle(angle);
        player.setBodyRotSpeed(angleSpeed);
        player.setBodyRotAccel(angleAccel);
    }
}
