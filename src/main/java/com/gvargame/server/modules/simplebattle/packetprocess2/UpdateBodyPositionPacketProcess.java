package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.Player;
import com.pro100kryto.server.logger.ILogger;
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
        final Vector3f pos = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
        final Vector3f posSpeed = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
        final Vector3f posAccel = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
        final Vector3f angle = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
        final Vector3f angleSpeed = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
        final Vector3f angleAccel = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());

        // save
        player.setBodyPos(pos);
        player.setBodySpeed(posSpeed);
        player.setBodyAccel(posAccel);
        player.setBodyAngle(angle);
        player.setBodyRotSpeed(angleSpeed);
        player.setBodyRotAccel(angleAccel);
    }
}
