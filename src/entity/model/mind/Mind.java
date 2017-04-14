package entity.model.mind;

import entity.model.*;
import entity.model.strategies.FleeMoveStrategy;
import entity.model.strategies.RandomMoveStrategy;

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

        workingMemory.clear();

        for (Thing perception : perceptions) {
            if (!workingMemory.contains(perception)) {
                workingMemory.add(perception);

                /*System.out.println("Living thing '" + this.being.getName()
                        + "' just saw '" + perception.getName() + "'.");*/

                /*while (workingMemory.size() > 4) {
                    workingMemory.remove(0);
                }*/
            }
        }

        if (being instanceof Rabbit) {
            Animal being = (Animal) this.being;
            Thing threat = null;

            for (Thing t : workingMemory) {
                if (t instanceof Wolf) threat = t;
            }

            if (threat != null) {
                if (being.getMoveStrategy() instanceof RandomMoveStrategy) {
                    being.setMoveStrategy(new FleeMoveStrategy(being, (Animal) threat));
                }
            } else {
                if (being instanceof Animal) {
                    being.setMoveStrategy(new RandomMoveStrategy(being));
                }
            }
        }

        // todo: do stuff
    }
}
