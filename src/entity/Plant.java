package entity;

import io.Window;
import org.joml.Vector2f;
import render.Camera;
import world.World;

public class Plant extends LivingThing {

	public Plant(String name, World world, Vector2f scale, Vector2f position) {
		super(name, world, 1, scale, position);
		
		this.setSolid(true);
	}

	@Override
	public void cycle(float delta) {}

	@Override
	public void update(float delta, Window window, Camera camera) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attack() {
        this.semantic.set("is-alive", false);
		this.semantic.set("when-dead", System.currentTimeMillis());
	}
}
