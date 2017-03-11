package entity.model;

import entity.model.mind.Mind;
import entity.model.mind.Sense;
import io.Window;
import org.joml.Vector2f;
import render.Camera;
import world.World;

import java.util.List;

public abstract class LivingThing extends Thing {	
	private boolean isAlive;
	private Mind mind;
	private List<Sense> senses;
	
	public LivingThing(String name, int amountAnim, Vector2f scale, Vector2f position) {
		super(name, amountAnim, scale, position);

		this.isAlive = true;
		this.mind = new Mind(this);
		this.senses = senses;
	}

    @Override
    public void update(float delta, Window window, Camera camera, World world) {
        this.mind.update();
    }

    public List<Sense> getSenses() {return this.senses;}
	public boolean isAlive() {return this.isAlive;}
}
