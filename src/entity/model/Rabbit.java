package entity.model;

import entity.model.mind.senses.GenericSight;
import org.joml.Vector2f;
import world.World;

public class Rabbit extends Mammal {
	
	public Rabbit(World world, Vector2f position) {
		super("rabbit", world, new Vector2f(2, 2), position);

		this.addSense(new GenericSight(this));
	}
}
