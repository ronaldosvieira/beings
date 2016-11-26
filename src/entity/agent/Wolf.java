package entity.agent;

import java.util.Random;

import entity.Transform;
import io.Window;
import render.Camera;
import world.World;

public class Wolf extends Agent {
	private float lastMove;
	
	public Wolf(Transform transform) {
		super("wolf", transform);
		
		this.lastMove = 0.0f;
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
}
