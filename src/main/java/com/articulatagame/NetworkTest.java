package com.articulatagame;

import java.io.IOException;

import com.articulatagame.network.NetworkUtil;
import com.esotericsoftware.kryonet.Client;

public class NetworkTest {


    public static void main(String[] args) {


        Client client = new Client();
        client.getKryo().register(String.class);
        client.start();
        try {
            client.connect(5000, "127.0.0.1", NetworkUtil.PORT_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException("Connection to server failed", e);
        }
        client.sendTCP(new String[]{"This", "is", "another", "test"});
    }
}
