package com.articulata;

import com.articulata.gamestate.GameStateMain;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAllocationCallbacks;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.VK10.*;

public class Articulata {

    private long window;
    private int width;
    private int height;
    private GameStateMain gameState;

    private VkInstance instance;
    private boolean initialized;

    public static VkInstance create(VkInstanceCreateInfo pCreateInfo, VkAllocationCallbacks pAllocator) {
        try (MemoryStack stack = stackPush()) {
            PointerBuffer pp = stack.mallocPointer(1);
            int err = vkCreateInstance(pCreateInfo, pAllocator, pp);
            if (err != VK_SUCCESS)
                throw new RuntimeException("...");

            return new VkInstance(pp.get(0), pCreateInfo);
        }
    }

    public static void main(String[] args) {
        new Articulata().run();
    }

    public void run() {
        initVulkan();
        loop();
    }

    private void initVulkan() {
        String name = "Some application";
        String engineName = "Some Engine name";
        VkApplicationInfo appInfo = VkApplicationInfo.calloc();
        appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO);
        appInfo.pNext(NULL);
        appInfo.apiVersion(VK_MAKE_VERSION(1, 0, 0));
        appInfo.applicationVersion(VK_MAKE_VERSION(1, 0, 0));
        appInfo.engineVersion(VK_MAKE_VERSION(1, 0, 0));

        ByteBuffer bufferApplicationName = BufferUtils.createByteBuffer(name.length() * 8);
        bufferApplicationName.put(name.getBytes());

        ByteBuffer bufferEngineName = BufferUtils.createByteBuffer(engineName.length() * 8);
        bufferEngineName.put(engineName.getBytes());

        appInfo.pApplicationName(bufferApplicationName);
        appInfo.pEngineName(bufferEngineName);

        VkInstanceCreateInfo instanceCreateInfo = VkInstanceCreateInfo.calloc();
        instanceCreateInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
        instanceCreateInfo.pNext(NULL);
        instanceCreateInfo.pApplicationInfo(appInfo);

        instance = create(instanceCreateInfo, null);

    }

    private void loop() {
        gameState = new GameStateMain();
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            gameState.update();
            gameState.render(width, height);
        }
    }
}