package entity.model;

import entity.model.mind.senses.Sight;
import org.joml.Vector2f;
import world.World;

public class Rabbit extends Mammal {
	
	public Rabbit(World world, Vector2f position) {
		super("rabbit", world, new Vector2f(2, 2), position);

		this.addSense(new Sight.SightBuilder(this).angle(270).build());
	}
}
