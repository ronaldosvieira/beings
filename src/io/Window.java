package io;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class Window {
	private long window;
	private int width, height;
	private boolean fullscreen;
	private boolean hasResized;
	private GLFWWindowSizeCallback windowSizeCallback;
	
	private Input input;
	
	public Window() {
		this.setSize(640, 480);
		this.setFullscreen(false);
		hasResized = false;
	}
	
	public void createWindow(String title) {
		window = glfwCreateWindow(
				width, 
				height, 
				title, 
				fullscreen? glfwGetPrimaryMonitor() : 0, 
				0);
		
		if (window == 0) throw new IllegalStateException("Failed to create window.");
		
		if (!fullscreen) {
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, 
					(vid.width() - width) / 2, 
					(vid.height() - height) / 2);
			
			glfwShowWindow(window);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		
		input = new Input(window);
		setLocalCallbacks();
	}
	
	public static void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	private void setLocalCallbacks() {
		windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long argWindow, int argWidth, int argHeight) {
				width = argWidth;
				height = argHeight;
				hasResized = true;
			}
		};
		
		glfwSetWindowSizeCallback(window, windowSizeCallback);
	}
	
	public void cleanUp() {
		glfwFreeCallbacks(window);
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public void update() {
		hasResized = false;
		input.update();
		glfwPollEvents();
	}
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public boolean hasResized() {return this.hasResized;}
	public boolean isFullscreen() {return this.fullscreen;}
	public long getWindow() {return this.window;}
	public Input getInput() {return this.input;}
}
