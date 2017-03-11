package entity.model.mind;

import entity.model.LivingThing;
import entity.model.mind.memory.MemoryFragment;

import java.util.List;

public abstract class Sense {
    private LivingThing being;

    public Sense(LivingThing being) {
        this.being = being;
    }

    abstract public List<MemoryFragment> perceive();
}
