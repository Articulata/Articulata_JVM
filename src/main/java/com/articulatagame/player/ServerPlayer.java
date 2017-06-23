package com.articulatagame.player;

import com.articulatagame.network.ServerConnection;

public class ServerPlayer extends Player {
    private final IPlayer clientPlayer;
    public final ServerConnection connection;

    public ServerPlayer(IPlayer clientPlayer, ServerConnection connection) {
        super(clientPlayer.getName());
        this.clientPlayer = clientPlayer;
        this.connection = connection;
    }
}
