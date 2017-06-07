package game;

import assets.Assets;
import entity.Entity;
import entity.model.Grass;
import entity.model.Rabbit;
import entity.model.Fox;
import frames.KnowledgeBase;
import gui.GUI;
import gui.PauseButton;
import io.Timer;
import io.Window;
import knowledge.FrameLoader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import render.Camera;
import render.TileRenderer;
import world.Map;
import world.World;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game {
	private World world;

	public Game() {}

    public World getWorld() {return this.world;}

    public void handleInput(float delta, Window window, Camera camera, World world) {
        float movement = 5 * 60 * delta;

        if (window.getInput().isKeyDown(GLFW_KEY_A)) {
            camera.addPosition(new Vector3f(movement, 0, 0));
        }

        if (window.getInput().isKeyDown(GLFW_KEY_D)) {
            camera.addPosition(new Vector3f(-movement, 0, 0));
        }

        if (window.getInput().isKeyDown(GLFW_KEY_W)) {
            camera.addPosition(new Vector3f(0, -movement, 0));
        }

        if (window.getInput().isKeyDown(GLFW_KEY_S)) {
            camera.addPosition(new Vector3f(0, movement, 0));
        }

        if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) ||
                window.getInput().getJoystickAxes(GLFW_JOYSTICK_6) > 0) {
            if (camera.getZoom() > .25) {
                camera.zoomIn();
                world.calculateView(camera);
            }
        }

        if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT) ||
                window.getInput().getJoystickAxes(GLFW_JOYSTICK_5) > 0) {
            if (camera.getZoom() < 3) {
                camera.zoomOut();
                world.calculateView(camera);
            }
        }

        if (window.getInput().getJoystickAxes(GLFW_JOYSTICK_1) != 0) {
            camera.addPosition(new Vector3f(
                    -movement * window.getInput().getJoystickAxes(GLFW_JOYSTICK_1), 0, 0));
        }

        if (window.getInput().getJoystickAxes(GLFW_JOYSTICK_2) != 0) {
            camera.addPosition(new Vector3f(
                    0, -movement * window.getInput().getJoystickAxes(GLFW_JOYSTICK_2), 0));
        }
    }

	public void start() {
		Window.setCallbacks();
		
		if (!glfwInit()) {
			System.err.println("GLFW failed to initialize.");
			System.exit(1);
		}
		
		Window window = new Window();
		window.createWindow("tcc");
		
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tr = new TileRenderer();
		
		Assets.initAsset();

		Shader shader = new Shader("shader");

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();

        try {
            KnowledgeBase.register("check-if-not-alive",
					f -> !f.get("is-alive", Boolean.class));

            FrameLoader.load("semantic");
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.exit(1);
        }

        int worldSize = 25;
        int worldScale = 16;

        int[][] tiles = new int[worldSize][worldSize];
        tiles[1][1] = 1;

		Map map = new Map(tiles, worldScale);
		
		this.world = new World(map);
		this.world.calculateView(camera);

		for (int i = 0; i < 45; i++) {
			this.world.addEntity(new Rabbit(this.world,
                    new Vector2f(
                            random.nextFloat() * worldSize,
                            -random.nextFloat() * worldSize)));
            this.world.addEntity(new Fox(this.world,
                    new Vector2f(
                            random.nextFloat() * worldSize,
                            -random.nextFloat() * worldSize)));
            this.world.addEntity(new Grass(this.world,
                    new Vector2f(
                            random.nextFloat() * worldSize,
                            -random.nextFloat() * worldSize)));
		}

        GUI pauseButton = new PauseButton();
		
		double frameCap = 1.0 / 60.0;
		double frameTime = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;

		boolean paused = false;
		boolean showFPS = false;

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
					camera.setSize(window.getWidth(), window.getHeight());
					this.world.calculateView(camera);
					glViewport(0, 0, window.getWidth(), window.getHeight());
				}
				
				unprocessed -= frameCap;
				canRender = true;

                if (window.getInput().isKeyPressed(GLFW_KEY_P)) paused = !paused;
                if (window.getInput().isKeyPressed(GLFW_KEY_F)) showFPS = !showFPS;

				handleInput((float) frameCap, window, camera, this.world);
				
				if (!paused) this.world.update((float) frameCap, window, camera);
				
				this.world.correctCamera(camera, window);
				
				window.update();
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					if (showFPS) System.out.println("FPS: " + frames);
					
					frames = 0;
				}
			}
			
			if (canRender) {
				glClear(GL_COLOR_BUFFER_BIT);
				
				this.world.render(tr, shader, camera);

				if (paused) pauseButton.render(camera);

				window.swapBuffers();
				
				frames++;
			}
		}
		
		Assets.deleteAsset();
		
		glfwTerminate();
	}

	public static void main(String[] args) {
		Game game = new Game();

		game.start();
	}
}
