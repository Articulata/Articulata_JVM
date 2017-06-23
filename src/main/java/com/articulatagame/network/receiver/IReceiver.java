package com.articulatagame.network.receiver;


import java.sql.Connection;
import java.util.UUID;

import com.articulatagame.util.Location;

public interface IReceiver {

    void receiveChat(String message);

    void updateLocation(Connection connection, UUID playerID, Location location);
}
