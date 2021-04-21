package com.gvargame.server.modules.simplebattle;

import com.pro100kryto.server.utils.datagram.packets.DataCreator;
import org.eclipse.collections.api.block.procedure.Procedure;

import javax.vecmath.Vector3f;

public class ProcedureIteratePlayers implements Procedure<Player> {
    private final DataCreator[] dataCreatorsPlayerPos;
    private int creatorSelectedPlayerPos = -1;
    private volatile int playersCount = 0;
    private int playersCountInProcess;

    private byte[] rawBytesPlayerPos;
    private int posBytesPlayerPos, lenBytesPlayerPos;


    public ProcedureIteratePlayers(int bufferSize) {
        dataCreatorsPlayerPos = new DataCreator[2];
        dataCreatorsPlayerPos[0] = new DataCreator(bufferSize);
        dataCreatorsPlayerPos[1] = new DataCreator(bufferSize);
        creatorSelectedPlayerPos = 0;
    }

    /**
     * update
     */
    @Override
    public void value(final Player player) {
        dataCreatorsPlayerPos[creatorSelectedPlayerPos].write(player.getConnId());

        player.getLocker().lock();

        writeVector(player.getBodyPos());
        writeVector(player.getBodySpeed());
        writeVector(player.getBodyAccel());

        writeVector(player.getBodyAngle());
        writeVector(player.getBodyRotSpeed());
        writeVector(player.getBodyRotAccel());

        writeVector(player.getGunAngle());
        writeVector(player.getGunRotSpeed());
        writeVector(player.getGunRotAccel());

        player.getLocker().unlock();

        playersCountInProcess++;
    }

    public synchronized void startNewIteration(){
        playersCountInProcess = 0;
        creatorSelectedPlayerPos = (1+ creatorSelectedPlayerPos)%2;
    }

    public synchronized void endIteration(){
        playersCount = playersCountInProcess;
        dataCreatorsPlayerPos[creatorSelectedPlayerPos].close();
        rawBytesPlayerPos = dataCreatorsPlayerPos[creatorSelectedPlayerPos].getDataContainer().getRaw();
        posBytesPlayerPos = dataCreatorsPlayerPos[creatorSelectedPlayerPos].getPosition();
        lenBytesPlayerPos = dataCreatorsPlayerPos[creatorSelectedPlayerPos].getDataLength();
    }

    private void writeVector(final Vector3f v3f){
        dataCreatorsPlayerPos[creatorSelectedPlayerPos].write(v3f.x);
        dataCreatorsPlayerPos[creatorSelectedPlayerPos].write(v3f.y);
        dataCreatorsPlayerPos[creatorSelectedPlayerPos].write(v3f.z);
    }

    public synchronized void writePlayerPositions(final DataCreator creatorOut){
        creatorOut.write(playersCount);
        // TODO: compress data or subdivide the packet??
        creatorOut.write(
                rawBytesPlayerPos,
                posBytesPlayerPos,
                lenBytesPlayerPos);
    }
}
