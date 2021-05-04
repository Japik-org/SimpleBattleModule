package com.pro100kryto.server.modules.simplebattle.packetprocess2;

import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.modules.simplebattle.MsgErrorCode;
import com.pro100kryto.server.modules.simplebattle.Player;
import com.pro100kryto.server.modules.simplebattle.packet.PacketCreator;
import com.pro100kryto.server.utils.datagram.packetprocess2.IPacketProcess;
import com.pro100kryto.server.utils.datagram.packets.*;

import javax.vecmath.Vector3f;

public abstract class PacketProcess implements IPacketProcess {
    protected final IPacketProcessCallback callback;
    protected final ILogger logger;

    protected PacketProcess(IPacketProcessCallback callback, ILogger logger) {
        this.callback = callback;
        this.logger = logger;
    }

    @Override
    public final void run(final IPacket packet) {
        try {

            final DataReader dataReader = packet.getDataReader();
            final Player player = callback.getPlayersArray().getPlayer(dataReader.readInt());
            if (!player.getConnectionInfo().getEndPoint().equals(packet.getEndPoint())){
                sendMsgError(packet.getEndPoint(), MsgErrorCode.Unauthorized);
                return;
            }
            try {
                player.getLocker().lock();
            } catch (NullPointerException nullPointerException){
                sendMsgError(new EndPoint(packet.getEndPoint()), MsgErrorCode.Unauthorized);
                return;
            }

            try {
                processPacket(packet, dataReader, player);
                player.getLocker().unlock();
            } catch (Throwable throwable) {
                try {
                    player.getLocker().unlock();
                } catch (IllegalMonitorStateException ignored) {
                }
                throw throwable;
            }


        } catch (Throwable throwable) {
            sendMsgError(new EndPoint(packet.getEndPoint()), MsgErrorCode.WrongPacket);
        }
    }

    private void sendMsgError(final IEndPoint endPoint, final MsgErrorCode code){
        try {
            final IPacketInProcess newPacket = callback.getPacketPool().getNextPacket();
            newPacket.setEndPoint(endPoint);
            try {
                PacketCreator.msgError(newPacket.getDataCreator(), code);
                callback.getSender().sendPacketAndRecycle(newPacket);
                return;
            } catch (NullPointerException ignored){
                logger.writeError("no connection with sender");
            }
            newPacket.recycle();
        } catch (NullPointerException nullPointerException){
            logger.writeError("packet pool is empty");
        }
    }

    public abstract void processPacket(IPacket packet, DataReader reader, Player player) throws Throwable;

    // ---------------

    public Vector3f readVector3f(final DataReader reader){
        return new Vector3f(reader.readFloat(), reader.readFloat(), reader.readFloat());
    }
}
