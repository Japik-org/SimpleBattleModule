package com.gvargame.server.modules.simplebattle.packetprocess2;

import com.gvargame.server.modules.simplebattle.PlayersArray;
import com.pro100kryto.server.modules.packetpool.connection.IPacketPoolModuleConnection;
import com.pro100kryto.server.modules.sender.connection.ISenderModuleConnection;
import org.jetbrains.annotations.Nullable;

public interface IPacketProcessCallback {
    @Nullable
    IPacketPoolModuleConnection getPacketPool();
    @Nullable
    ISenderModuleConnection getSender();
    PlayersArray getPlayersArray();
}
