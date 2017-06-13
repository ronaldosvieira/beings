package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import entity.model.mind.sense.TemporalPerception;
import org.joml.Vector2f;

import java.util.List;
import java.util.Random;

public class Freeze extends Goal {
	public Freeze(Animal animal) {
		super(animal);

		animal.setMovementSpeed(0);

		direction = animal.getCurrentDirection();
    }

	@Override
	public void cycle(List<TemporalPerception> workingMemory) {}

	@Override
	public boolean check() {return false;}
}
