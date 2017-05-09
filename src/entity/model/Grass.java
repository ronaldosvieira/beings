package entity.model;

import entity.Transform;
import org.joml.Vector2f;
import render.Animation;
import world.World;

public class Grass extends Plant {
	public Grass(World world, Vector2f position) {
		super("grass", world, new Vector2f(2, 2), position);
		
		this.setWalkable(true);
		
		setAnimation(0, new Animation(1, 1,
				"entities/" + getName()));
	}

}
