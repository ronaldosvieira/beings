package entity.model;

import entity.model.mind.senses.GenericVision;
import io.Window;
import org.joml.Vector2f;
import render.Camera;
import world.World;

public class Wolf extends Mammal {
	
	public Wolf(Vector2f position) {
		super("wolf", new Vector2f(3, 3), position);

		this.addSense(new GenericVision(this));
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
        super.update(delta, window, camera, world);

		move(delta, this.movement.getMovement(delta));
	}
}
