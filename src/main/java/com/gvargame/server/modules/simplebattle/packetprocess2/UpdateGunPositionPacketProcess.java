package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.Player;
import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.utils.datagram.packets.DataReader;
import com.pro100kryto.server.utils.datagram.packets.IPacket;

import javax.vecmath.Vector3f;

public class UpdateGunPositionPacketProcess extends PacketProcess{


    public UpdateGunPositionPacketProcess(IPacketProcessCallback callback, ILogger logger) {
        super(callback, logger);
    }

    @Override
    public void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable {
        final Vector3f angle = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
        final Vector3f angleSpeed = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
        final Vector3f angleAccel = new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());

        player.setGunAngle(angle);
        player.setGunRotSpeed(angleSpeed);
        player.setGunRotAccel(angleAccel);
    }
}
