package entity;

import org.joml.Vector2f;
import render.Animation;
import world.World;

public class GenericPlant extends Plant {
	private boolean hasFruit;

	public GenericPlant(World world, Vector2f position) {
		super("generic-plant", world, new Vector2f(2, 3), position);
		
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
