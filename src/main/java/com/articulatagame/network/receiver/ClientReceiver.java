package com.articulatagame.network.receiver;

import java.sql.Connection;
import java.util.UUID;

import com.articulatagame.util.Location;

public class ClientReceiver implements IReceiver {

    @Override
    public void receiveChat(String message) {
        System.out.println(message);
    }

    @Override
    public void updateLocation(Connection connection, UUID playerID, Location location) {

    }
}
