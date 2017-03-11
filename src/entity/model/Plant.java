package entity.model;

import entity.Transform;
import io.Window;
import org.joml.Vector2f;
import render.Animation;
import render.Camera;
import world.World;

public class Plant extends LivingThing {

	public Plant(String name, Vector2f scale, Vector2f position) {
		super(name, 1, scale, position);
		
		this.setSolid(true);
	}

	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		// TODO Auto-generated method stub

	}

}
