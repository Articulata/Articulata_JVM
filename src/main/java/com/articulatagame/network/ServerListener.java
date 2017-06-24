package com.articulatagame.network;


import com.articulatagame.ArticulataServer;
import com.articulatagame.network.receiver.IReceiver;
import com.articulatagame.object.player.IPlayer;
import com.articulatagame.object.player.ServerPlayer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class ServerListener extends Listener {

    @Override
    public void connected(Connection connection) {
        System.out.println("Connection established, grabbing client listener...");
        final ServerConnection scon = (ServerConnection) connection;
        IReceiver receiver = ObjectSpace.getRemoteObject(connection, NetworkUtil.CLIENT_RECEIVER, IReceiver.class);
        final IPlayer player = ObjectSpace.getRemoteObject(connection, NetworkUtil.IPLAYER, IPlayer.class);
        new ObjectSpace(connection).register(NetworkUtil.SERVER_RECEIVER, ArticulataServer.INSTANCE.receiver);
        scon.init(player, receiver);

        ArticulataServer.INSTANCE.tasks.add(() -> {
            ArticulataServer.INSTANCE.addPlayer(new ServerPlayer(player, scon));
        });


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