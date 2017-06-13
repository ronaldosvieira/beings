package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.joml.Vector2f;

import java.util.Random;

public class Freeze extends Goal {
	public Freeze(Animal animal) {
		super(animal);

		animal.setMovementSpeed(0);

		direction = animal.getCurrentDirection();
    }

	@Override
	public void cycle() {}

	@Override
	public boolean check() {return false;}
}
