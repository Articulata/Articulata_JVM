package com.articulatagame.network;

import com.articulatagame.network.receiver.ServerReceiver;
import com.esotericsoftware.kryonet.Connection;

public class ClientConnection extends Connection {
    public ServerReceiver receiver;

    public ClientConnection() {

    }


}


