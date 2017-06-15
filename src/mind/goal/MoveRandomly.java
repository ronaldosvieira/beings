package mind.goal;

import java.util.List;
import java.util.Random;

import mind.sense.TemporalPerception;
import org.joml.Vector2f;

import entity.Animal;

public class MoveRandomly extends Goal {
    private long lastMove;
    private long moveTime;
    private boolean isMoving;
	
	public MoveRandomly(Animal animal) {
		super(animal);
		
		direction = getRandomDirection();
		isMoving = false;
		lastMove = System.currentTimeMillis();
		moveTime = getRandomMoveTime();
	}

	@Override
	public void cycle(List<TemporalPerception> workingMemory) {
		long current = System.currentTimeMillis();

        if (current - lastMove >= moveTime) {
            lastMove = current;
            moveTime = getRandomMoveTime();

            if (!isMoving) direction = getRandomDirection();

            isMoving = !isMoving;
		}
		
		if (isMoving) {
            getAnimal().setMovementSpeed(5);
        } else getAnimal().setMovementSpeed(0);
	}

	@Override
	public boolean check() {return false;}

	private Vector2f getRandomDirection() {
		Random random = new Random();
		Vector2f dir = new Vector2f(
				random.nextFloat() * 2 - 1, 
				random.nextFloat() * 2 - 1);
		
		return dir.normalize();
	}

    private long getRandomMoveTime() {
	    float base = 0.5f;
	    float modifier = (new Random().nextFloat() * 0.2f) - 0.1f;

        return (long) ((base + modifier) * 1000L);
    }
}
