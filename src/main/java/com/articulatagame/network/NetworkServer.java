package com.articulatagame.network;

import com.articulatagame.ArticulataServer;
import com.articulatagame.network.receiver.ServerReceiver;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class NetworkServer extends Server {
    private final ServerReceiver receiver;

    public NetworkServer(ArticulataServer server) {
        super();
        this.receiver = new ServerReceiver();
        NetworkUtil.registerClasses(getKryo());
    }

    @Override
    protected Connection newConnection() {
        return new ServerConnection();
    }
}
