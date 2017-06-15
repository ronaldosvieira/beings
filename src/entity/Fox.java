package entity;

import mind.need.Hunger;
import mind.need.Security;
import mind.sense.Sight;
import org.joml.Vector2f;
import world.World;

public class Fox extends Mammal {
	
	public Fox(World world, Vector2f position) {
		super("fox", world, new Vector2f(3, 3), position);

		this.addSense(new Sight.SightBuilder(this).angle(120).range(25).build());
        this.addNeed(new Hunger(this, 1.0 / 5.0, 0.15));
        this.addNeed(new Security(this));
	}
}
