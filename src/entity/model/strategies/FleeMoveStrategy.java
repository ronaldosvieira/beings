package entity.model.strategies;

import entity.model.Animal;
import org.joml.Vector2f;

import java.util.Random;

public class FleeMoveStrategy extends MoveStrategy {
	private Animal enemy;

	public FleeMoveStrategy(Animal animal, Animal enemy) {
		super(animal);

		this.enemy = enemy;
    }

	@Override
	public Vector2f getMovement(float delta) {
		return animal.getPosition()
                .sub(enemy.getPosition())
                .normalize();
	}
}
