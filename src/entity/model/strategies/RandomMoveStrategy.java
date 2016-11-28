package entity.model.strategies;

import java.util.Random;

import org.joml.Vector2f;

import entity.model.Animal;

public class RandomMoveStrategy extends MoveStrategy {
	
	public RandomMoveStrategy(Animal animal) {
		super(animal);
	}

	@Override
	public Vector2f getMovement(float delta) {
		Random random = new Random();
		
		if (animal.isMoving()) {
			return new Vector2f(.0f, .0f);
		} else {
			while (true) {
				Vector2f direction = new Vector2f(
						random.nextFloat() * 2 - 1, 
						random.nextFloat() * 2 - 1);
				
				if (direction.length() <= 1.0f) {
					return direction.normalize();
				}
			}
		}
	}

}
