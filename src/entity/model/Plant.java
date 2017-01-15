package entity.model;

import entity.Transform;
import io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class Plant extends LivingThing {

	public Plant(String name, Transform transform) {
		super(name, 1, transform);
		
		this.setSolid(true);
	}

	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		// TODO Auto-generated method stub

	}

}
