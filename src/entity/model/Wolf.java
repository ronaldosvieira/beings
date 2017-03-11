package entity.model;

import entity.Transform;
import io.Window;
import org.joml.Vector2f;
import render.Camera;
import world.World;

public class Wolf extends Mammal {
	
	public Wolf(Vector2f position) {
		super("wolf",
                new Vector2f(3, 3),
                position);
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		move(delta, this.movement.getMovement(delta));
	}
}
