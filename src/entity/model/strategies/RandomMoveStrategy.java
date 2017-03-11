package entity.model.strategies;

import java.util.Random;

import org.joml.Vector2f;

import entity.model.Animal;

public class RandomMoveStrategy extends MoveStrategy {
	Vector2f direction;
	float lastMove;
	boolean isMoving;
	
	public RandomMoveStrategy(Animal animal) {
		super(animal);
		
		this.direction = getRandomDirection();
		this.isMoving = false;
		this.lastMove = 0.0f;
	}

	@Override
	public Vector2f getMovement(float delta) {
		lastMove += delta;
		
		animal.setMovementSpeed(getRandomSpeed());
		
		if (lastMove >= 0.5f) {
			lastMove = 0.0f;
			isMoving = !isMoving;
			direction = getRandomDirection();
		}
		
		if (isMoving) return direction;
		else return new Vector2f(0, 0);
	}
	
	private Vector2f getRandomDirection() {
		Random random = new Random();
		Vector2f dir = new Vector2f(
				random.nextFloat() * 2 - 1, 
				random.nextFloat() * 2 - 1);
		
		return dir.normalize();
	}

	private float getRandomSpeed() {
		return new Random().nextFloat() * 7.5f;
	}
}
