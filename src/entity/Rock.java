package entity;

import io.Window;
import org.joml.Vector2f;
import render.Animation;
import render.Camera;
import world.World;

public class Rock extends Thing {
	public Rock(World world, Vector2f position) {
		super("rock", world, 1, new Vector2f(2, 2), position);
		
		setAnimation(0, new Animation(1, 1,
				"entities/" + getName()));

		this.setSolid(true);
	}

	@Override
	public void update(float delta, Window window, Camera camera) {

	}

	@Override
	public void cycle(float delta) {

	}
}
