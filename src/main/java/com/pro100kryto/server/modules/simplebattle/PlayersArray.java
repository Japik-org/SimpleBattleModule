package com.pro100kryto.server.modules.simplebattle;


import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.jetbrains.annotations.Nullable;

public class PlayersArray {
    private final IntObjectHashMap<Player> connIdPlayerMap = new IntObjectHashMap<>();

    public PlayersArray(){

    }

    public void addPlayer(final int connId, final Player player){
        connIdPlayerMap.put(connId, player);
    }

    public void removePlayer(final int connId){
        connIdPlayerMap.remove(connId);
    }

    @Nullable
    public Player getPlayer(final int connId){
        return connIdPlayerMap.get(connId);
    }

    public boolean existsPlayer(final int connId){
        return connIdPlayerMap.contains(connId);
    }

    public void iteratePlayers(final Procedure<Player> consumer){
        connIdPlayerMap.forEachValue(consumer);
    }

    public void removeAll(){
        connIdPlayerMap.clear();
    }
}
