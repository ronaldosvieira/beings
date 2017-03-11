package entity.model.mind;

import entity.model.LivingThing;
import entity.model.Thing;

import java.util.List;

public abstract class Sense {
    private LivingThing being;

    public Sense(LivingThing being) {
        this.being = being;
    }

    abstract public List<Thing> perceive();

    public LivingThing getBeing() {return this.being;}
}
