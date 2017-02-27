package entity.model;

import entity.Transform;
import io.Window;
import render.Camera;
import world.World;

public class Wolf extends Mammal {
	
	public Wolf(Transform transform) {
		super("wolf", transform);
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		move(delta, this.movement.getMovement(delta));
	}
}
