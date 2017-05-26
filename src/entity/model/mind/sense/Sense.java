package entity.model.mind.sense;

import entity.model.LivingThing;

import java.util.List;

public abstract class Sense {
    private LivingThing being;

    public Sense(LivingThing being) {
        this.being = being;
    }

    abstract public List<Perception> perceive();

    public LivingThing getBeing() {return this.being;}
}
