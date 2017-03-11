package entity.model.mind;

import entity.model.LivingThing;
import entity.model.Thing;

import java.util.ArrayList;
import java.util.List;

public class Mind {
    private LivingThing being;

    public Mind(LivingThing being) {
        this.being = being;
    }

    public void update() {
        List<Thing> perceptions = new ArrayList<>();
        for (Sense sense : this.being.getSenses()) {
            perceptions.addAll(sense.perceive());
        }

        // todo: do stuff
    }
}
