package entity.model.mind;

import entity.model.LivingThing;
import entity.model.mind.memory.MemoryFragment;

import java.util.List;

public class Mind {
    private LivingThing being;

    public Mind(LivingThing being) {
        this.being = being;
    }

    public void update() {
        for (Sense sense : this.being.getSenses()) {
            List<MemoryFragment> perceptions = sense.perceive();

            // todo: do stuff
        }
    }
}
