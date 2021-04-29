package com.gvargame.server.modules.simplebattle;

import com.pro100kryto.server.utils.datagram.packets.DataCreator;

import javax.vecmath.Vector3f;

public class IteratedPlayersData {
    private final DataCreator creatorPositions;
    private final byte[] rawDataPositions;
    private int positionsDataOffset, positionsDataLen;
    private int playersCount;

    public IteratedPlayersData(int creatorPositionsBufferSize) {
        this.creatorPositions = new DataCreator(creatorPositionsBufferSize);
        rawDataPositions = creatorPositions.getDataContainer().getRaw();
        positionsDataOffset = 0;
    }

    public void writeCountAndPlayerPositions(DataCreator creatorDataOut){
        creatorDataOut.write(playersCount);
        creatorDataOut.write(rawDataPositions, positionsDataOffset, positionsDataLen);
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void reset(){
        creatorPositions.reset();
        playersCount=0;
    }

    public void finish(){
        creatorPositions.close();
        //positionsDataOffset = creatorPositions.getPosition();
        positionsDataLen = creatorPositions.getDataLength();
    }

    public void readPlayer(Player player){
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

        playersCount++;
    }

    private void writeVector(final Vector3f v3f){
        creatorPositions.write(v3f.x);
        creatorPositions.write(v3f.y);
        creatorPositions.write(v3f.z);
    }
}
