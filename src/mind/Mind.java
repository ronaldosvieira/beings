package mind;

import entity.Animal;
import mind.goal.*;
import mind.need.Need;
import mind.sense.Perception;
import mind.sense.Sense;
import mind.sense.TemporalPerception;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mind {
    private Animal being;
    private List<TemporalPerception> workingMemory;
    private Need currentNeed;

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

        //System.out.println(being.getName() + being.getId() + ": " + workingMemory);

        // todo: do stuff based on current working memory
        /*for (TemporalPerception tP : workingMemory) {
            if (!tP.get("is-alive", Boolean.class)) continue;

            double size1 = tP.get("size", Double.class);
            double size2 = being.getSemantic().get("size", Double.class);

            if (size1 < size2) {
                if (being.getCurrentGoal() instanceof MoveRandomly) {
                    Goal newGoal = new GoalChain(new MoveTo(being))
                            .then(new Attack(being))
                            .input(tP)
                            .get();
                    being.setCurrentGoal(newGoal);
                    System.out.println(being + " -> " + tP.name());
                }
            }
        }*/

        Need mostIntenseNeed = being.getNeeds().stream()
                .sorted(Comparator.comparingDouble(Need::getIntensity).reversed())
                .findFirst()
                .orElse(null);

        if (mostIntenseNeed != currentNeed)
            being.setCurrentGoal(mostIntenseNeed.getGoal());

        currentNeed = mostIntenseNeed;

        //if (being.getName().equals("rabbit")) {
            boolean tavivo = being.getSemantic().get("is-alive", Boolean.class);
            if (!tavivo) System.out.println(being.getName() + " ta morto gente");
        //}

        Goal currentGoal = being.getCurrentGoal();
        currentGoal.cycle(workingMemory);

        if (currentGoal.isCompleted()) {
            System.out.println(being.getName() + " " + currentGoal + " completed");

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
