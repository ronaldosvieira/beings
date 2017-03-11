package entity.model.mind;

import entity.model.LivingThing;
import entity.model.Thing;
import entity.model.mind.senses.Sense;

import java.util.ArrayList;
import java.util.List;

public class Mind {
    private LivingThing being;
    private List<Thing> workingMemory;

    public Mind(LivingThing being) {
        this.being = being;
        this.workingMemory = new ArrayList<>();
    }

    public void update() {
        List<Thing> perceptions = new ArrayList<>();
        for (Sense sense : this.being.getSenses()) {
            perceptions.addAll(sense.perceive());
        }

        for (Thing perception : perceptions) {
            if (!workingMemory.contains(perception)) {
                workingMemory.add(perception);

                System.out.println("Living thing '" + this.being.getName()
                        + "' just saw '" + perception.getName() + "'.");

                while (workingMemory.size() > 4) {
                    workingMemory.remove(0);
                }
            }
        }

        // todo: do stuff
    }
}
