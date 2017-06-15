package entity;

import org.joml.Vector2f;
import world.World;

public abstract class LivingThing extends Thing {	
	private boolean isAlive;

	public LivingThing(String name, World world, int amountAnim, Vector2f scale, Vector2f position) {
		super(name, world, amountAnim, scale, position);

		this.isAlive = true;
	}

	public boolean isAlive() {return this.isAlive;}
	abstract public void attack();
}
