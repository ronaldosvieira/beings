package entity.model;

import entity.Transform;
import io.Window;
import render.Camera;
import world.World;

public class Rabbit extends Mammal {
	
	public Rabbit(Transform transform) {
		super("rabbit", transform);
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		move(delta, this.movement.getMovement(delta));
	}
}
