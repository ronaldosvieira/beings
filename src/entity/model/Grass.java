package entity.model;

import entity.Transform;
import render.Animation;

public class Grass extends Plant {
	public Grass(Transform transform) {
		super("grass", transform);
		
		this.setWalkable(true);
		
		setAnimation(0, new Animation(1, 1,
				"entities/" + getName()));
	}

}
