package io;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	private long window;
	private boolean keys[];
	
	private static int GLFW_KEY_FIRST = 32;
	
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
		
		for (int i = GLFW_KEY_FIRST; i < GLFW_KEY_LAST; i++) {
			keys[i] = false;
		}
	}
	
	public boolean isKeyDown(int key) {
		return glfwGetKey(window, key) == 1;
	}
	
	public boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}
	
	public boolean isKeyPressed(int key) {
		return isKeyDown(key) && !keys[key];
	}
	
	public boolean isKeyReleased(int key) {
		return !isKeyDown(key) && keys[key];
	}

	public boolean isJoystickButtonDown(int button) {
	    ByteBuffer buttons = glfwGetJoystickButtons(GLFW_JOYSTICK_1);

	    return buttons != null && buttons.get(button) == 1;
    }

    public float getJoystickAxes(int button) {
	    FloatBuffer axes = glfwGetJoystickAxes(GLFW_JOYSTICK_1);

	    return axes != null? axes.get(button) : 0.0f;
    }

	public void update() {
		for (int i = GLFW_KEY_FIRST; i < GLFW_KEY_LAST; i++) {
			keys[i] = isKeyDown(i);
		}
	}
}
