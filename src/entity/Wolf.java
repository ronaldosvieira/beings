package entity;

import java.util.Random;

import org.joml.Vector2f;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class Wolf extends Entity {
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
	
	private float speed;
	private float lastMove;
	private Vector2f direction;
	
	public Wolf(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE_N, new Animation(1, 1, "wolf/idle_N"));
		setAnimation(ANIM_IDLE_S, new Animation(1, 1, "wolf/idle_S"));
		setAnimation(ANIM_IDLE_W, new Animation(1, 1, "wolf/idle_W"));
		setAnimation(ANIM_IDLE_E, new Animation(1, 1, "wolf/idle_E"));
		setAnimation(ANIM_WALK_N, new Animation(3, 8, "wolf/walking_N"));
		setAnimation(ANIM_WALK_S, new Animation(3, 8, "wolf/walking_S"));
		setAnimation(ANIM_WALK_W, new Animation(3, 8, "wolf/walking_W"));
		setAnimation(ANIM_WALK_E, new Animation(3, 8, "wolf/walking_E"));
		
		this.speed = 4f;
		this.lastMove = 0.0f;
		this.direction = new Vector2f(0, 0);
	}
	
	public void move(float delta) {
		Vector2f movement = new Vector2f();
		movement.add(speed * delta * direction.x, 
				speed * delta * direction.y);
		super.move(movement);
	}
	
	public void updateAnimation() {
		if (direction.length() > 0) {
			if (Math.abs(direction.x) > Math.abs(direction.y)) {
				if (direction.x > 0) useAnimation(ANIM_WALK_E);
				else useAnimation(ANIM_WALK_W);
			} else {
				if (direction.y > 0) useAnimation(ANIM_WALK_N);
				else useAnimation(ANIM_WALK_S);
			}
		} else {
			if (lastAnim >= 4) useAnimation(lastAnim - 4);
		}
	}

	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Random random = new Random();
		
		if (lastMove < random.nextFloat() * 2.0f + 1.0f) {
			lastMove += delta;
		} else {
			lastMove = 0.0f;
			
			if (direction.length() == 0) {
				this.direction.set(random.nextFloat() * 2 - 1, 
						random.nextFloat() * 2 - 1).normalize();
			} else {
				direction.set(0.0f, 0.0f);
			}
		}
		
		updateAnimation();
		move(delta);
	}

	@Override
	public void useAnimation(int index) {
		this.lastAnim = index;
		super.useAnimation(index);
	}
}
