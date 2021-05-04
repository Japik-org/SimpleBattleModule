package com.pro100kryto.server.modules.simplebattle.packetprocess2;

import com.pro100kryto.server.modules.packetpool.connection.IPacketPoolModuleConnection;
import com.pro100kryto.server.modules.sender.connection.ISenderModuleConnection;
import com.pro100kryto.server.modules.simplebattle.PlayersArray;
import org.jetbrains.annotations.Nullable;

public interface IPacketProcessCallback {
    @Nullable
    IPacketPoolModuleConnection getPacketPool();
    @Nullable
    ISenderModuleConnection getSender();
    PlayersArray getPlayersArray();
}
