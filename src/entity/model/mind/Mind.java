package entity.model.mind;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import entity.model.mind.sense.Sense;
import entity.model.mind.sense.TemporalPerception;

import java.util.ArrayList;
import java.util.List;

public class Mind {
    private Animal being;
    private List<TemporalPerception> workingMemory;

    public Mind(Animal being) {
        this.being = being;
        this.workingMemory = new ArrayList<>();
    }

    public void update() {
        List<Perception> perceptions = new ArrayList<>();

        for (Sense sense : this.being.getSenses()) {
            perceptions.addAll(sense.perceive());
        }

        for (int i = 0; i < workingMemory.size(); i++) {
            TemporalPerception tempPerception = workingMemory.get(i);

            perceptions.stream()
                    .filter(perception -> perception.getSource().getId() == tempPerception.getSource().getId())
                    .findFirst()
                    .ifPresent(tempPerception::update);

            // workingMemory.set(i, tempPerception);
        }

        // todo: try to merge perceptions
        //      if many of same type then merge all with distance = centroid
        //      if distance and type == those of an old perception then merge
        // todo: weigh perceptions relevance

        for (Perception perception : perceptions) {
            if (!workingMemory.contains(perception)) {
                workingMemory.add(new TemporalPerception(perception));

                /*System.out.println("Living thing '" + this.being.getName()
                        + "' just saw '" + perception.name() + "'.");*/

                while (workingMemory.size() > 4) {
                    workingMemory.remove(0);
                }
            }
        }

        // todo: do stuff based on current working memory
    }
}
