package com.lkadalski.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private String windowTitle;
    private int width;
    private int height;
    private boolean vSync;
    private long windowHandle;
    private boolean resized;

    public Window(String windowTitle, int width, int height, boolean vSync) {

        this.windowTitle = windowTitle;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
    }


    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }


    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        windowHandle = glfwCreateWindow(width, height, windowTitle, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window!");
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        //get the resolution of primary monitor
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        //center our window
        glfwSetWindowPos(windowHandle,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2);

        //make the openGl context current
        glfwMakeContextCurrent(windowHandle);

        if (isvSync()){
            glfwSwapInterval(1);
        }

        //male the window visible

        glfwShowWindow(windowHandle);

        GL.createCapabilities();
        glClearColor(0.0f,0.0f,0.0f,0.0f);


    }
    public void setClearColor(float r, float g, float b, float alpha){
        glClearColor(r,g,b,alpha);
    }
    public boolean windowShouldClose(){
        return glfwWindowShouldClose(windowHandle);
    }
    public String getTitle() {
        return windowTitle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }
    public void update(){
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }
}