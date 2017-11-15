package entity;

import mind.need.Hunger;
import mind.need.Reproduction;
import mind.need.Safety;
import mind.sense.Hearing;
import mind.sense.Sight;
import org.joml.Vector2f;
import world.World;

public class Fox extends Mammal {
	
	public Fox(World world, Vector2f position) {
		super("fox", world, new Vector2f(3, 3), position);

		this.addSense(new Sight.Builder(this).angle(120).range(25).build());
		this.addSense(new Hearing.Builder(this).range(20).build());

        this.addNeed(new Hunger(this, 1.0 / 5.0, 0.15));
        this.addNeed(new Safety(this));
		this.addNeed(new Reproduction(this, 1.0 / 10.0, 0.1));
	}
}
