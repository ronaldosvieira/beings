package entity.model;

import entity.model.mind.senses.GenericSight;
import org.joml.Vector2f;
import world.World;

public class Fox extends Mammal {
	
	public Fox(World world, Vector2f position) {
		super("fox", world, new Vector2f(3, 3), position);

		this.addSense(new GenericSight(this, 90));
	}
}
