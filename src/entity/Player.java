package entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector2f;
import org.joml.Vector3f;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class Player extends Entity {

	public Player(Transform transform) {
		super(new Animation(5, 15, "an"), transform);
	}
	
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		
		if (window.getInput().isKeyDown(GLFW_KEY_A)) {
			movement.add(-10 * delta, 0);
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_D)) {
			movement.add(10 * delta, 0);
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_W)) {
			movement.add(0, 10 * delta);
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_S)) {
			movement.add(0, -10 * delta);
		}
		
		move(movement);

		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.075f);
		//camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
	}
}
