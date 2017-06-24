package com.articulatagame;

import com.articulatagame.network.ClientListener;
import com.articulatagame.network.NetworkUtil;
import com.articulatagame.network.receiver.IReceiver;
import com.articulatagame.object.player.ClientPlayer;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class ArticulataClient {
    public static ArticulataClient INSTANCE;
    public ClientPlayer player;
    public IReceiver serverLink;
    public Client client;


    public ArticulataClient(String playerName) {
        player = new ClientPlayer(playerName);
        client = new Client();
        NetworkUtil.registerClasses(client.getKryo());
        client.addListener(new ClientListener());
        client.start();
        try {
            client.connect(5000, "127.0.0.1", NetworkUtil.PORT_NUMBER);
        } catch (IOException e) {
            //TODO: try again, waiting on vulcan gui for that
            throw new RuntimeException("Failed to connect to server", e);
        }
    }

    public static void main(String[] args) {
        String name;
        if (args.length == 1) {
            name = args[0];
        } else {
            name = "John Doe";
        }

        INSTANCE = new ArticulataClient(name);

        while (true) {
            //keeping it alive
        }
    }
}
