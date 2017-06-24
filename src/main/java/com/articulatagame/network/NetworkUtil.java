package com.articulatagame.network;

import com.articulatagame.network.receiver.IReceiver;
import com.articulatagame.object.player.IPlayer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import java.util.Arrays;
import java.util.UUID;

public class NetworkUtil {
    public static final int PORT_NUMBER = 42666;
    public static final int CLIENT_RECEIVER = 1;
    public static final int SERVER_RECEIVER = 2;
    public static final int IPLAYER = 3;

    private static final Class[] syncedClasses = {
        String[].class,
        IPlayer.class,
        IReceiver.class,
        UUID.class
    };

    public static void registerClasses(Kryo kryo) {
        ObjectSpace.registerClasses(kryo);
        Arrays.stream(syncedClasses).forEach(kryo::register);
    }


}
