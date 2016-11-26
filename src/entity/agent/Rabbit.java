package entity.agent;

import java.util.Random;

import org.joml.Vector2f;

import entity.Transform;
import io.Window;
import render.Camera;
import world.World;

public class Rabbit extends Agent {
	private float lastMove;
	
	public Rabbit(Transform transform) {
		super("rabbit", transform);
		
		this.lastMove = 0.0f;
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		if (lastMove < 0.5f) {
			lastMove += delta;
			
			move(delta);
		} else {
			lastMove = 0.0f;

			move(delta, this.movement.getMovement(delta));
		}
	}
}
