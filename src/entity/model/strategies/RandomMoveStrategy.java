package entity.model.strategies;

import java.util.Random;

import org.joml.Vector2f;

import entity.model.Animal;

public class RandomMoveStrategy extends MoveStrategy {
	private Vector2f direction;
    private float lastMove;
    private float moveTime;
    private boolean isMoving;
	
	public RandomMoveStrategy(Animal animal) {
		super(animal);
		
		this.direction = getRandomDirection();
		this.isMoving = false;
		this.lastMove = 0.0f;
		this.moveTime = getRandomMoveTime();
    }

	@Override
	public Vector2f getMovement(float delta) {
		lastMove += delta;

        if (lastMove >= moveTime) {
			lastMove = 0.0f;
            moveTime = getRandomMoveTime();
			isMoving = !isMoving;
			direction = getRandomDirection();
		}
		
		if (isMoving) animal.setMovementSpeed(0);
        else animal.setMovementSpeed(5);

        return direction;
	}
	
	private Vector2f getRandomDirection() {
		Random random = new Random();
		Vector2f dir = new Vector2f(
				random.nextFloat() * 2 - 1, 
				random.nextFloat() * 2 - 1);
		
		return dir.normalize();
	}

    private float getRandomMoveTime() {
	    float base = 0.5f;
	    float modifier = (new Random().nextFloat() * 0.2f) - 0.1f;

	    return base + modifier;
    }
}
