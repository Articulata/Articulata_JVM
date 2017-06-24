package com.articulatagame;

import java.io.IOException;
import java.nio.IntBuffer;

import com.articulatagame.gamestate.GameStateMain;
import com.articulatagame.network.ClientListener;
import com.articulatagame.network.NetworkUtil;
import com.articulatagame.network.receiver.IReceiver;
import com.articulatagame.object.player.ClientPlayer;
import com.esotericsoftware.kryonet.Client;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ArticulataClient {
    public static ArticulataClient INSTANCE;
    public ClientPlayer player;
    public IReceiver serverLink;
    public Client client;
    private LogicThread logicThread;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private long window;
    private GameStateMain gameState = new GameStateMain();



    public ArticulataClient(String playerName) {
        player = new ClientPlayer(playerName);
        client = new Client();
        NetworkUtil.registerClasses(client.getKryo());
        client.addListener(new ClientListener());
        client.start();
        try {
            client.connect(5000, "127.0.0.1", NetworkUtil.PORT_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to server", e);
        }
    }

    private void start() {
        initWindow();
        logicThread = new LogicThread("Client", gameState);
        logicThread.start();
        loop();
        cleanup();
    }

    private void initWindow() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();

        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );

            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);


            glfwShowWindow(window);
        }
    }

    private void loop() {
        createCapabilities();
        gameState = new GameStateMain();
        glEnable(GL_DEPTH_TEST);
        glShadeModel(GL_SMOOTH);
        glEnable(GL_COLOR_MATERIAL);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glViewport(0, 0, WIDTH, HEIGHT);
        glTranslatef(0.0f, 0.0f, -1.0f);
        glClearColor(255, 255, 255, 255);

        glOrtho(0.0, WIDTH, 0.0, HEIGHT, -10.0, 10.0);
        glMatrixMode(GL_MODELVIEW);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwPollEvents();
            gameState.update();
            gameState.render(WIDTH, HEIGHT);
            glfwSwapBuffers(window);
        }
    }

    private void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();

        logicThread.shutdown();
        client.close();
    }

    public static void main(String[] args) {
        String name;
        if (args.length == 1) {
            name = args[0];
        } else {
            name = "John Doe";
        }

        INSTANCE = new ArticulataClient(name);
        INSTANCE.start();
    }
}
