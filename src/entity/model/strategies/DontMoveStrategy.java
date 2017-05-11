package entity.model.strategies;

import entity.model.Animal;
import org.joml.Vector2f;

import java.util.Random;

public class DontMoveStrategy extends MoveStrategy {
	public DontMoveStrategy(Animal animal) {
		super(animal);

		animal.setMovementSpeed(0);
    }

	@Override
	public Vector2f getMovement(float delta) {
        return animal.getCurrentDirection();
	}
}
