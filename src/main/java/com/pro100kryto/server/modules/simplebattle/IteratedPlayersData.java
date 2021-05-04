package com.pro100kryto.server.modules.simplebattle;

import com.pro100kryto.server.utils.datagram.packets.DataCreator;

import javax.vecmath.Vector3f;

public class IteratedPlayersData {
    private int playersCount;

    private final DataCreator creatorPositions;
    private final byte[] rawDataPositions;
    private int positionsDataOffset, positionsDataLen;

    private final DataCreator creatorList;
    private final byte[] rawDataList;
    private int listDataOffset, listDataLen;

    public IteratedPlayersData(int creatorPositionsBufferSize, int creatorListBufferSize) {
        this.creatorPositions = new DataCreator(creatorPositionsBufferSize);
        rawDataPositions = creatorPositions.getDataContainer().getRaw();
        positionsDataOffset = 0;

        creatorList = new DataCreator(creatorListBufferSize);
        rawDataList = creatorList.getDataContainer().getRaw();
        listDataOffset = 0;
    }

    public void writeCountAndPlayerPositions(DataCreator creatorDataOut){
        creatorDataOut.write(playersCount);
        creatorDataOut.write(rawDataPositions, positionsDataOffset, positionsDataLen);
    }

    public void writeCountAndPlayersList(DataCreator creatorDataOut){
        creatorDataOut.write(playersCount);
        creatorDataOut.write(rawDataList, listDataOffset, listDataLen);
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

        creatorList.close();
        //listDataOffset = creatorList.getPosition();
        listDataLen = creatorList.getDataLength();
    }

    public void readPlayer(Player player){

        creatorList.write(player.getConnectionInfo().getConnId());
        creatorList.writeShortStrings(player.getConnectionInfo().getNickname());

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
