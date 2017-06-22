package com.articulatagame;

import java.io.IOException;

import com.articulatagame.network.ServerListener;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class NetworkTest {
    private static final int PORT_NUMBER = 42666;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.getKryo().register(String[].class);
        try {
            server.bind(PORT_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException("Server port bind failed", e);
        }


        server.addListener(new ServerListener());

        Client client = new Client();
        client.start();
        client.getKryo().register(String.class);
        try {
            client.connect(5000, "127.0.0.1", PORT_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException("Connection to server failed", e);
        }
        client.sendTCP(new String[]{"This", "is", "another", "test"});

    }
}
