import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.awt.*;
import java.nio.*;
import java.util.HashMap;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;

public class Articulata {

    public Sprite sprite;

    private long window;
    private HashMap<Integer, Entity> entities;
    private int width;
    private int height;

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        this.width = 600;
        this.height = 600;
        window = glfwCreateWindow(width, height, "Kikkers zijn graaf...", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

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
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

    }

    private void loop() {
        GL.createCapabilities();
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        this.entities = new HashMap<>();
        sprite = new Sprite(0xFFFFaa, new Rectangle(width / 2 - 100, height / 2 - 100, 100, 100));
        entities.put(0, new Entity(sprite));
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            render();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void render() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 500, 500, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glEnable(GL_TEXTURE_2D);
        glColor3f(1, 1, 1);
        glBegin(GL_LINES);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(1.0f, 1.0f);
        glEnd();
        GL11.glBegin(GL11.GL_QUADS);
        float u = 0f;
        float v = 0f;
        float u2 = 1f;
        float v2 = 1f;
        glColor4f(1f, 1f, 1f, 1f);
        for (Entity entity : entities.values()) {
            Rectangle rct = entity.getSprite().getRect();
            glBindTexture(GL_TEXTURE_2D, entity.getSprite().getId());
            glTexCoord2f(u, v);
            glVertex2f(rct.x, rct.y);
            glTexCoord2f(u, v2);
            glVertex2f(rct.x, rct.y + rct.height);
            glTexCoord2f(u2, v2);
            glVertex2f(rct.x + rct.width, rct.y + rct.height);
            glTexCoord2f(u2, v);
            glVertex2f(rct.x + rct.width, rct.y);
        }
        GL11.glEnd();

        glEnd();
    }

    public static void main(String[] args) {
        new Articulata().run();
    }
}