package entity.model;

import org.joml.Vector2f;

public abstract class LivingThing extends Thing {	
	private boolean isAlive;
	
	public LivingThing(String name, int amountAnim, Vector2f scale, Vector2f position) {
		super(name, amountAnim, scale, position);

		this.isAlive = true;
	}

	public boolean isAlive() {return this.isAlive;}
}
