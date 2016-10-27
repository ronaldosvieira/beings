package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

public class Game {
	public Game() {
		if (!glfwInit()) {
			System.err.println("GLFW failed to initialize.");
			System.exit(1);
		}
		
		long window = glfwCreateWindow(640, 480, "Game", 0, 0);
		
		glfwShowWindow(window);
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		
		float[] vertices = new float[] {
			-0.5f, 0.5f, 0, // 0
			0.5f, 0.5f, 0,  // 1
			0.5f, -0.5f, 0, // 2
			-0.5f, -0.5f, 0,// 3
		};
		
		float[] texture = new float[] {0,0, 1,0, 1,1, 0,1};
		int[] indexes = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		Model m0 = new Model(vertices, texture, indexes);
		Shader sh0 = new Shader("shader");
		Texture t0 = new Texture("./res/sample2.png");
		
		while (!glfwWindowShouldClose(window)) {
			if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwSetWindowShouldClose(window, true);
			}
			
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			sh0.bind();
			sh0.setUniform("sampler", 0);
			t0.bind(0);
			m0.render();
			
			glfwSwapBuffers(window);
		}
		
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
