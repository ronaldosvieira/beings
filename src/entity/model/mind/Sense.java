package entity.model.mind;

import entity.model.LivingThing;
import model.InstanceFrame;

import java.util.List;

public abstract class Sense {
    private LivingThing being;

    public Sense(LivingThing being) {
        this.being = being;
    }

    abstract public List<InstanceFrame> perceive();

    public LivingThing getBeing() {return this.being;}
}
