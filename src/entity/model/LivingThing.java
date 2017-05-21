package entity.model;

import entity.model.mind.Sense;
import org.joml.Vector2f;
import world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class LivingThing extends Thing {	
	private boolean isAlive;

	private List<Sense> senses;
	
	public LivingThing(String name, World world, int amountAnim, Vector2f scale, Vector2f position) {
		super(name, world, amountAnim, scale, position);

		this.isAlive = true;
		this.senses = new ArrayList<>();
	}

    public List<Sense> getSenses() {return this.senses;}
	public boolean isAlive() {return this.isAlive;}

	protected void addSense(Sense sense) {
	    if (!this.senses.contains(sense)) this.senses.add(sense);
    }
}
