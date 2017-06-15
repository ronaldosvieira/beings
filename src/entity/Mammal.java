package entity;

import org.joml.Vector2f;
import world.World;

public abstract class Mammal extends Animal {

	public Mammal(String name, World world, Vector2f scale, Vector2f position) {
		super(name, world, scale, position);
	}

}
