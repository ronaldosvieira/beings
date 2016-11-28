package entity.model;

import entity.Transform;

public abstract class LivingThing extends Thing {	
	private boolean isAlive;
	
	public LivingThing(String name, int amountAnim, Transform transform) {
		super(name, amountAnim, transform);
		this.isAlive = true;
		// TODO Auto-generated constructor stub
	}

	public boolean isAlive() {return this.isAlive;}
}
