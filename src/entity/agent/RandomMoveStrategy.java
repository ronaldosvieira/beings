package entity.agent;

import java.util.Random;

import org.joml.Vector2f;

public class RandomMoveStrategy extends MoveStrategy {
	
	public RandomMoveStrategy(Agent agent) {
		super(agent);
	}

	@Override
	public Vector2f getMovement(float delta) {
		Random random = new Random();
		
		if (agent.isMoving()) {
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
