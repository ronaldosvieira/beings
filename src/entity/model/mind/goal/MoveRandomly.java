package entity.model.mind.goal;

import java.util.Random;

import entity.model.mind.sense.Perception;
import org.joml.Vector2f;

import entity.model.Animal;

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
	public void cycle() {
		long current = System.currentTimeMillis();

        if (current - lastMove >= moveTime) {
            lastMove = current;
            moveTime = getRandomMoveTime();

            if (isMoving) direction = getRandomDirection();

            isMoving = !isMoving;
		}
		
		if (isMoving) getAnimal().setMovementSpeed(0);
        else getAnimal().setMovementSpeed(5);
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
