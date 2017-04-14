package entity.model;

import entity.model.mind.senses.GenericVision;
import io.Window;
import org.joml.Vector2f;
import render.Camera;
import world.World;

public class Rabbit extends Mammal {
	
	public Rabbit(Vector2f position) {
		super("rabbit", new Vector2f(2, 2), position);

		this.addSense(new GenericVision(this));
	}
}
