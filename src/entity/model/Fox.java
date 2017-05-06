package entity.model;

import entity.model.mind.senses.GenericVision;
import io.Window;
import org.joml.Vector2f;
import render.Camera;
import world.World;

public class Fox extends Mammal {
	
	public Fox(Vector2f position) {
		super("fox", new Vector2f(3, 3), position);

		this.addSense(new GenericVision(this));
	}
}
