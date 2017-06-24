package com.articulatagame;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import com.articulatagame.gamestate.GameStateMain;
import com.articulatagame.network.NetworkServer;
import com.articulatagame.network.NetworkUtil;
import com.articulatagame.network.ServerListener;
import com.articulatagame.network.receiver.ServerReceiver;
import com.articulatagame.object.player.ServerPlayer;

public class ArticulataServer {
    public static final ArticulataServer INSTANCE = new ArticulataServer();
    public final ServerReceiver receiver = new ServerReceiver();
    public final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private NetworkServer networkServer;
    private List<ServerPlayer> players = new CopyOnWriteArrayList<>();

    private GameStateMain gameState = new GameStateMain();
    private LogicThread logicThread;

    public static void main(String[] args) {
        INSTANCE.start();
    }

    private void cleanup() {
        logicThread.shutdown();
        networkServer.close();
    }

    private void start() {
        networkServer = new NetworkServer(this);
        try {
            networkServer.bind(NetworkUtil.PORT_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException("Server port bind failed", e);
        }
        networkServer.addListener(new ServerListener());

        networkServer.start();

        logicThread = new LogicThread("Client", gameState);
        logicThread.start();


    }

    public void addPlayer(ServerPlayer player) {
        sendChatToAll(player.name + " joined the game");
        players.add(player);
        System.out.println(player.name + " joined the game");
    }

    public void sendChatToAll(String message) {
        players.forEach(
            serverPlayer -> serverPlayer.connection.getReceiver().receiveChat(message));
    }
}
