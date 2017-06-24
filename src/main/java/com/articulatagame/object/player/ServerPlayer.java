package com.articulatagame.object.player;

import com.articulatagame.network.ServerConnection;

public class ServerPlayer extends Player {
    public final ServerConnection connection;
    private final IPlayer clientPlayer;

    public ServerPlayer(IPlayer clientPlayer, ServerConnection connection) {
        super(clientPlayer.getName());
        this.clientPlayer = clientPlayer;
        this.connection = connection;
    }
}
