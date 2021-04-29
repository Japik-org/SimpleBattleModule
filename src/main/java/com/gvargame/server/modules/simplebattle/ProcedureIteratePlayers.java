package com.gvargame.server.modules.simplebattle;

import com.pro100kryto.server.utils.datagram.packets.DataCreator;
import org.eclipse.collections.api.block.procedure.Procedure;

import java.util.concurrent.locks.ReentrantLock;

public class ProcedureIteratePlayers implements Procedure<Player> {

    private final ReentrantLock locker = new ReentrantLock();
    private final int dataArrayLen;
    private final IteratedPlayersData[] iteratedPlayersData;
    private int playersDataSelectedIndex = 0;
    private IteratedPlayersData iteratedPlayersDataSelected;
    private IteratedPlayersData iteratedPlayersDataReady;


    public ProcedureIteratePlayers(int bufferSize, int buffersCount) {
        dataArrayLen = buffersCount;
        iteratedPlayersData = new IteratedPlayersData[dataArrayLen];
        for (int i = 0; i < dataArrayLen; i++) {
            iteratedPlayersData[i] = new IteratedPlayersData(bufferSize);
        }
        iteratedPlayersDataSelected = iteratedPlayersData[playersDataSelectedIndex];
        iteratedPlayersDataReady = null;
    }

    /**
     * update
     */
    @Override
    public void value(final Player player) {
        iteratedPlayersDataSelected.readPlayer(player);
    }

    public synchronized void startNewIterationAndLock(){
        locker.lock();
        playersDataSelectedIndex = (1+playersDataSelectedIndex)% dataArrayLen;
        iteratedPlayersDataSelected = iteratedPlayersData[playersDataSelectedIndex];
        iteratedPlayersDataSelected.reset();
    }

    public synchronized void finishIterationAndUnlock(){
        iteratedPlayersDataSelected.finish();
        iteratedPlayersDataReady = iteratedPlayersDataSelected;
        locker.unlock();
    }

    public synchronized void cancelIterationAndUnlock(){
        playersDataSelectedIndex = (playersDataSelectedIndex-1)% dataArrayLen;
        iteratedPlayersDataSelected = iteratedPlayersData[playersDataSelectedIndex];
        locker.unlock();
    }


    public synchronized void writeCountAndPlayerPositions(final DataCreator creatorDataOut){
        iteratedPlayersDataReady.writeCountAndPlayerPositions(creatorDataOut);
    }

    public int getPlayersCount() {
        return iteratedPlayersDataReady.getPlayersCount();
    }
}
