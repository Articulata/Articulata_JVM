package com.articulatagame.network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {
    @Override
    public void connected(Connection connection) {
        System.out.println("Connection established");
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Connection terminated");
    }

    @Override
    public void received(Connection connection, Object object) {
        System.out.println("Incomming transmition: " + object);
    }

    @Override
    public void idle(Connection connection) {
    }
}