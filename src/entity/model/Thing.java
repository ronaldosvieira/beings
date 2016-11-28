package entity.model;

import entity.Entity;
import entity.Transform;

public abstract class Thing extends Entity {
	private String name;

	public Thing(String name, int amountAnim, Transform transform) {
		super(amountAnim, transform);
		
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	public String getName() {return this.name;}
}
