package entity.model;

import org.joml.Vector2f;
import render.Animation;

public class GenericPlant extends Plant {
	private boolean hasFruit;

	public GenericPlant(Vector2f position) {
		super("generic-plant", new Vector2f(2, 3), position);
		
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
