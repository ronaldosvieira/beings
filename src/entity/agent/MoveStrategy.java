package entity.agent;

import org.joml.Vector2f;

public abstract class MoveStrategy {
	protected Agent agent;
	
	public MoveStrategy(Agent agent) {
		this.agent = agent;
	}
	
	public abstract Vector2f getMovement(float delta);
}
