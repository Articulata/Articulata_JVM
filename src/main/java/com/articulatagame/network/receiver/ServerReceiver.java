package com.articulatagame.network.receiver;

import java.sql.Connection;
import java.util.UUID;

import com.articulatagame.ArticulataServer;
import com.articulatagame.util.Location;

public class ServerReceiver implements IReceiver {

    @Override
    public void receiveChat(String message) {
        System.out.println("Chat message received (" + message + "), relaying to clients");
        ArticulataServer.INSTANCE.sendChatToAll(message);
    }

    @Override
    public void updateLocation(Connection connection, UUID playerID, Location location) {

    }
}
