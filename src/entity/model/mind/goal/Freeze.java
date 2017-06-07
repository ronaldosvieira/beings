package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.joml.Vector2f;

import java.util.Random;

public class Freeze extends Goal {
	public Freeze(Animal animal, Perception perception) {
		super(animal, perception);

		animal.setMovementSpeed(0);
    }

	@Override
	public Vector2f getMovement(float delta) {
        return getAnimal().getCurrentDirection();
	}

	@Override
	public boolean isCompleted() {return false;}
}
