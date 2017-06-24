package com.articulatagame.network;

import com.articulatagame.network.receiver.IReceiver;
import com.articulatagame.object.player.IPlayer;
import com.esotericsoftware.kryonet.Connection;

public class ServerConnection extends Connection {
    private IPlayer player;
    private IReceiver receiver;

    public void init(IPlayer player, IReceiver receiver) {
        this.player = player;
        this.receiver = receiver;
    }

    public IPlayer getPlayer() {
        return player;
    }

    public IReceiver getReceiver() {
        return receiver;
    }
}
