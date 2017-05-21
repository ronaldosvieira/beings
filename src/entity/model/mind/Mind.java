package entity.model.mind;

import entity.model.Animal;
import model.InstanceFrame;

import java.util.ArrayList;
import java.util.List;

public class Mind {
    private Animal being;
    private List<InstanceFrame> workingMemory;

    public Mind(Animal being) {
        this.being = being;
        this.workingMemory = new ArrayList<>();
    }

    public void update() {
        List<InstanceFrame> perceptions = new ArrayList<>();

        for (Sense sense : this.being.getSenses()) {
            perceptions.addAll(sense.perceive());
        }

        // todo: try to merge perceptions

        for (InstanceFrame perception : perceptions) {
            if (!workingMemory.contains(perception)) {
                workingMemory.add(perception);

                /*System.out.println("Living thing '" + this.being.getName()
                        + "' just saw '" + perception.getName() + "'.");*/

                while (workingMemory.size() > 4) {
                    workingMemory.remove(0);
                }
            }
        }

        // todo: do stuff
    }
}
