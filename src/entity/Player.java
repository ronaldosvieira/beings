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
	public static final int ANIM_IDLE_N = 0;
	public static final int ANIM_IDLE_S = 1;
	public static final int ANIM_IDLE_W = 2;
	public static final int ANIM_IDLE_E = 3;
	public static final int ANIM_WALK_N = 4;
	public static final int ANIM_WALK_S = 5;
	public static final int ANIM_WALK_W = 6;
	public static final int ANIM_WALK_E = 7;
	public static final int ANIM_SIZE = 8;
	private int lastAnim;
	
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE_N, new Animation(1, 1, "rabbit/idle_N"));
		setAnimation(ANIM_IDLE_S, new Animation(1, 1, "rabbit/idle_S"));
		setAnimation(ANIM_IDLE_W, new Animation(1, 1, "rabbit/idle_W"));
		setAnimation(ANIM_IDLE_E, new Animation(1, 1, "rabbit/idle_E"));
		setAnimation(ANIM_WALK_N, new Animation(3, 8, "rabbit/walking_N"));
		setAnimation(ANIM_WALK_S, new Animation(3, 8, "rabbit/walking_S"));
		setAnimation(ANIM_WALK_W, new Animation(3, 8, "rabbit/walking_W"));
		setAnimation(ANIM_WALK_E, new Animation(3, 8, "rabbit/walking_E"));
	}
	
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		
		if (window.getInput().isKeyDown(GLFW_KEY_A)) {
			movement.add(-10 * delta, 0);
			useAnimation(ANIM_WALK_W);
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_D)) {
			movement.add(10 * delta, 0);
			useAnimation(ANIM_WALK_E);
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_W)) {
			movement.add(0, 10 * delta);
			useAnimation(ANIM_WALK_N);
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_S)) {
			movement.add(0, -10 * delta);
			useAnimation(ANIM_WALK_S);
		}
		
		move(movement);
		
		if (movement.x == 0 && movement.y == 0 && lastAnim >= 4) {
			useAnimation(lastAnim - 4);
		}

		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.075f);
		//camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
	}
	
	@Override
	public void useAnimation(int index) {
		this.lastAnim = index;
		super.useAnimation(index);
	}
}
