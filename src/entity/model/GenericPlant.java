package entity.model;

import java.nio.file.Paths;

import entity.Transform;
import render.Animation;

public class GenericPlant extends Plant {
	private boolean hasFruit;

	public GenericPlant(Transform transform) {
		super("generic-plant", transform);
		
		this.hasFruit = true;

		updateAnimation();
	}
	
	public boolean hasFruit() {
		return this.hasFruit;
	}
	
	public void setHasFruit(boolean hasFruit) {
		this.hasFruit = hasFruit;
		
		updateAnimation();
	}

	private void updateAnimation() {
		String fruit = this.hasFruit? "fruit" : "no-fruit";

		setAnimation(0, new Animation(1, 1,
				"entities/" + getName() + "/fruit"));
	}

}
