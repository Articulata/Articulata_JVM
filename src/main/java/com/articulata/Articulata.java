package com.articulata;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.articulata.gamestate.GameStateMain;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkQueueFamilyProperties;

import static org.lwjgl.glfw.GLFW.GLFW_CLIENT_API;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_NO_API;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.VK10.VK_MAKE_VERSION;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;
import static org.lwjgl.vulkan.VK10.vkCreateDevice;
import static org.lwjgl.vulkan.VK10.vkCreateInstance;
import static org.lwjgl.vulkan.VK10.vkDestroyDevice;
import static org.lwjgl.vulkan.VK10.vkDestroyInstance;
import static org.lwjgl.vulkan.VK10.vkEnumeratePhysicalDevices;
import static org.lwjgl.vulkan.VK10.vkGetDeviceQueue;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceQueueFamilyProperties;

public class Articulata {

    private long window;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    //TODO: purpose?
    private GameStateMain gameState = new GameStateMain();

    private VkInstance instance;
    private VkPhysicalDevice physicalDevice;
    private VkDevice device;
    private VkQueue graphicsQueue;
    private boolean initialized;

    public static void main(String[] args) {
        new Articulata().run();
    }

    public void run() {
        initWindow();
        initVulkan();
        loop();
        cleanup();
    }

    private void initWindow() {
        //initialize glwf
        glfwInit();

        //we want OpenGL context
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);

        //no window resizing
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        //create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Articulata", NULL, NULL);
    }


    private void initVulkan() {
        setupVulcan();
        setupPhisicalDevice();
        createLogicalDevice();
    }

    private void createLogicalDevice() {
        try (MemoryStack memstack = MemoryStack.stackPush()) {
            ByteBuffer buffer = memstack.calloc(1);
            VkDeviceQueueCreateInfo.Buffer info = VkDeviceQueueCreateInfo.callocStack(1, memstack);
            info.sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO);
            IntBuffer intBuffer = memstack.callocInt(1);
            vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, intBuffer, null);
            int familyCount = intBuffer.get();
            intBuffer = memstack.callocInt(1);
            intBuffer.put(intBuffer.position(), familyCount);

            VkQueueFamilyProperties.Buffer faminfo = VkQueueFamilyProperties.calloc(familyCount);
            vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, intBuffer, faminfo);


            //info.queueFamilyIndex(family);
            FloatBuffer floatBuffer = memstack.callocFloat(1);
            //floatBuffer.put(1);
            info.pQueuePriorities(floatBuffer);
            VkQueueFamilyProperties

            VkDeviceCreateInfo createInfo = VkDeviceCreateInfo.create();
            createInfo.sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO);
            createInfo.pQueueCreateInfos(info);
            createInfo.pEnabledFeatures(VkPhysicalDeviceFeatures.create());

            PointerBuffer pointerBuffer = memstack.callocPointer(1);
            vkCreateDevice(physicalDevice, createInfo, null, pointerBuffer);
            device = new VkDevice(pointerBuffer.get(), physicalDevice, createInfo);

            PointerBuffer anotherPoint = memstack.callocPointer(1);
            //vkCreateQueryPool(device, createInfo, null, longBuffer);
            vkGetDeviceQueue(device, family, 0, anotherPoint);
            graphicsQueue = new VkQueue(anotherPoint.get(), device);
        }
    }

    private void setupPhisicalDevice() {


        //memorystack to push to, used with try to have it cleared up after
        try (MemoryStack memstack = MemoryStack.stackPush();) {
            IntBuffer buffer = buffer = memstack.mallocInt(1);


            long result = vkEnumeratePhysicalDevices(instance, buffer, null);
            int count = buffer.get();
            if (count == 0) {
                throw new RuntimeException("No physical support for vulcan available!");
            }

            System.out.println(count);
            buffer = memstack.mallocInt(1);
            buffer.put(buffer.position(), 1);
            PointerBuffer deviceBuffer = memstack.mallocPointer(1);
            vkEnumeratePhysicalDevices(instance, buffer, deviceBuffer);
            //TODO: select the best one
            physicalDevice = new VkPhysicalDevice(deviceBuffer.get(), instance);
        }
    }

    private void setupVulcan() {
        VkApplicationInfo appInfo = createAppInfo();

        //non optional info class, this one will be for identifying global extionsion and validations layers to use (for entire program, not a physicalDevice)
        VkInstanceCreateInfo instanceCreateInfo = VkInstanceCreateInfo.create();
        instanceCreateInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
        //link (optional) app info from earlier to the instance info
        instanceCreateInfo.pApplicationInfo(appInfo);

        //get the required extensions for GLFW
        PointerBuffer requiredInstanceExtensions = glfwGetRequiredInstanceExtensions();
        instanceCreateInfo.ppEnabledExtensionNames(requiredInstanceExtensions);


        PointerBuffer buffer = PointerBuffer.allocateDirect(1);
        long result = vkCreateInstance(instanceCreateInfo, null, buffer);
        if (result != VK_SUCCESS) {
            throw new RuntimeException("Vulcan creation failed (code " + result + "), startup failed");
        }
        instance = new VkInstance(buffer.get(), instanceCreateInfo);
    }

    private VkApplicationInfo createAppInfo() {
        String name = "Articulata";
        String engineName = "Articulata engine";


        //create app info, mostly optional, main purpose is GPU recognition for optimalization and keeping track
        VkApplicationInfo appInfo = VkApplicationInfo.create();
        appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO);

        //API version of the engine
        appInfo.apiVersion(VK_MAKE_VERSION(1, 0, 0));
        appInfo.applicationVersion(VK_MAKE_VERSION(1, 0, 0));
        appInfo.engineVersion(VK_MAKE_VERSION(1, 0, 0));

        //identify application name and used engine for GPU
        ByteBuffer bufferApplicationName = BufferUtils.createByteBuffer(name.length() * 8);
        bufferApplicationName.put(name.getBytes());

        ByteBuffer bufferEngineName = BufferUtils.createByteBuffer(engineName.length() * 8);
        bufferEngineName.put(engineName.getBytes());

        appInfo.pApplicationName(bufferApplicationName);
        appInfo.pEngineName(bufferEngineName);

        return appInfo;
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            gameState.update();
            gameState.render(WIDTH, HEIGHT);
        }
    }

    private void cleanup() {
        //destroy vulcan
        vkDestroyInstance(instance, null);
        vkDestroyDevice(device, null);


        //window needs to be destoryed seperatly (as you are not limited to 1)
        glfwDestroyWindow(window);

        //complete glfw termination
        glfwTerminate();
    }
}