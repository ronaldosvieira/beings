package entity.model.strategies;

import org.joml.Vector2f;

import entity.model.Animal;

public abstract class MoveStrategy {
	protected Animal animal;
	
	public MoveStrategy(Animal animal) {
		this.animal = animal;
	}
	
	public abstract Vector2f getMovement(float delta);
}
