package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import entity.Entity;
import io.Timer;
import io.Window;
import render.Camera;
import render.TileRenderer;
import world.Map;
import world.World;

public class Game {
	public Game() {
		Window.setCallbacks();
		
		if (!glfwInit()) {
			System.err.println("GLFW failed to initialize.");
			System.exit(1);
		}
		
		Window window = new Window();
		window.createWindow("Game");
		
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tr = new TileRenderer();
		
		Entity.initAsset();

		Shader shader = new Shader("shader");
		
		Map map = new Map(new int[50][50]);
		
		World world = new World(map);
		world.calculateView(window);
		
		double frameCap = 1.0 / 60.0;
		double frameTime = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		while (!window.shouldClose()) {
			if (window.getInput().isKeyPressed(GLFW_KEY_ESCAPE)) {
				glfwSetWindowShouldClose(window.getWindow(), true);
			}
			
			boolean canRender = false;
			
			double time2 = Timer.getTime();
			double passed = time2 - time;
			unprocessed += passed;
			
			frameTime += passed;
			
			time = time2;
			
			while (unprocessed >= frameCap) {
				if (window.hasResized()) {
					camera.setProjection(window.getWidth(), window.getHeight());
					world.calculateView(window);
					glViewport(0, 0, window.getWidth(), window.getHeight());
				}
				
				unprocessed -= frameCap;
				canRender = true;
				
				world.update((float) frameCap, window, camera);
				
				world.correctCamera(camera, window);
				
				window.update();
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					System.out.println("FPS: " + frames);
					
					frames = 0;
				}
			}
			
			if (canRender) {
				glClear(GL_COLOR_BUFFER_BIT);
				
//				sh0.bind();
//				sh0.setUniform("sampler", 0);
//				sh0.setUniform("projection", camera.getProjection().mul(target));
//				t0.bind(0);
//				m0.render();
				
				world.render(tr, shader, camera);
				
				window.swapBuffers();
				
				frames++;
			}
		}
		
		Entity.deleteAsset();
		
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
