package entity.model;

import entity.Transform;
import org.joml.Vector2f;
import render.Animation;

public class Grass extends Plant {
	public Grass(Vector2f position) {
		super("grass", new Vector2f(2, 2), position);
		
		this.setWalkable(true);
		
		setAnimation(0, new Animation(1, 1,
				"entities/" + getName()));
	}

}
