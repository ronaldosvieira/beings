package entity.model.mind;

import entity.model.Animal;
import entity.model.mind.goal.*;
import entity.model.mind.sense.Perception;
import entity.model.mind.sense.Sense;
import entity.model.mind.sense.TemporalPerception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mind {
    private Animal being;
    private List<TemporalPerception> workingMemory;

    public Mind(Animal being) {
        this.being = being;
        this.workingMemory = new ArrayList<>();
    }

    public void update() {
        Map<Integer, Perception> perceptions = this.being.getSenses().stream()
                .map(Sense::perceive)
                .flatMap(Collection::stream)

                // combines perceptions from same source
                .collect(Collectors.toMap(p -> p.getSource().getId(), Function.identity(), Perception::combine));

        // updates perceptions in working memory
        workingMemory.stream()
                .filter(temporalPerception -> perceptions.containsKey(temporalPerception.getSource().getId()))
                .forEach(tP -> {
                    int id = tP.getSource().getId();
                    Perception p = perceptions.get(id);

                    tP.update(p);
                    perceptions.remove(id);
                });

        // todo: try to merge perceptions
        //      if many of same type then merge all with distance = centroid
        // todo: weigh perceptions relevance

        for (Perception perception : perceptions.values()) {
            workingMemory.add(new TemporalPerception(perception));

            /*System.out.println("Living thing '" + this.being.getName()
                    + "' just saw '" + perception.name() + "'.");*/

            while (workingMemory.size() > 4) {
                workingMemory.remove(0);
            }
        }

        // todo: do stuff based on current working memory

        Goal currentGoal = being.getCurrentGoal();

        if (currentGoal.isCompleted()) {
            System.out.println("completed");

            Goal next = currentGoal.next();

            if (next != null)
                next.input(currentGoal.output());

            being.setCurrentGoal(next);
        }

        List<Goal> preReqs = currentGoal.preReqs();

        while (preReqs.size() > 0) {
            Goal preReq = preReqs.get(0);
            currentGoal = being.getCurrentGoal();
            preReq.next(currentGoal);

            being.setCurrentGoal(preReq.input(currentGoal.output()));

            preReqs = being.getCurrentGoal().preReqs();
        }
    }
}
