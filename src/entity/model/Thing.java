package entity.model;

import entity.Entity;
import org.joml.Vector2f;
import world.World;

public abstract class Thing extends Entity {
	private String name;

	public Thing(String name, World world, int amountAnim, Vector2f scale, Vector2f position) {
		super(world, amountAnim, scale, position);
		
		this.name = name;
	}

	public String getName() {return this.name;}
}
