package entity.model;

import entity.Transform;
import io.Window;
import org.joml.Vector2f;
import render.Camera;
import world.World;

public class Rabbit extends Mammal {
	
	public Rabbit(Vector2f position) {
		super("rabbit", new Vector2f(2, 2), position);
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		move(delta, this.movement.getMovement(delta));
	}
}
