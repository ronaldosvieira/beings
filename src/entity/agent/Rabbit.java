package entity.agent;

import java.util.Random;

import org.joml.Vector2f;

import entity.Transform;
import io.Window;
import render.Camera;
import world.World;

public class Rabbit extends Agent {
	private float lastMove;
	private boolean isMoving;
	
	public Rabbit(Transform transform) {
		super("rabbit", transform);
		
		this.lastMove = 0.0f;
		this.isMoving = false;
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Random random = new Random();
		
		if (lastMove < random.nextFloat() + 1.0f) {
			lastMove += delta;
			
			move(delta);
		} else {
			lastMove = 0.0f;
			
			if (isMoving) {
				move(delta, new Vector2f(.0f, .0f));
				isMoving = false;
			} else {
				move(delta, new Vector2f(
						random.nextFloat() * 2 - 1, 
						random.nextFloat() * 2 - 1)
						.normalize());
				isMoving = true;
			}
		}
	}
}
