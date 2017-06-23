package com.articulatagame.network;

import com.articulatagame.ArticulataClient;
import com.articulatagame.network.receiver.ClientReceiver;
import com.articulatagame.network.receiver.IReceiver;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class ClientListener extends Listener {


    @Override
    public void connected(Connection connection) {
        System.out.println("Connection established, setting up rmi...");
        ObjectSpace space = new ObjectSpace(connection);
        space.register(NetworkUtil.CLIENT_RECEIVER, new ClientReceiver());
        space.register(NetworkUtil.IPLAYER, ArticulataClient.INSTANCE.player);
        ArticulataClient.INSTANCE.serverLink = ObjectSpace.getRemoteObject(connection, NetworkUtil.SERVER_RECEIVER, IReceiver.class);
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Connection terminated");
    }

    @Override
    public void received(Connection connection, Object object) {
        //System.out.println("Incomming transmition: " + object);

    }

    @Override
    public void idle(Connection connection) {
    }
}
