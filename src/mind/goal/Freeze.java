package mind.goal;

import entity.Animal;
import mind.sense.TemporalPerception;

import java.util.List;

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
