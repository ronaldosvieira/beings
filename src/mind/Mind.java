package mind;

import entity.Animal;
import mind.goal.*;
import mind.need.Need;
import mind.sense.Perception;
import mind.sense.Sense;
import mind.sense.TemporalPerception;
import org.joml.Vector2f;

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
                .collect(Collectors.toMap(p -> p.getSource().getId(),
                        Function.identity(),
                        Perception::combine));

        // weigh perceptions relevance
        perceptions.forEach((id, p) ->
                being.getNeeds().forEach(need ->
                        p.set(need.getName(), need.evaluate(p))));

        // todo: try to merge perceptions
        //      if many of same type then merge all with distance = centroid

//        System.out.println(being.getName() + " before -> " + perceptions);

        // updates perceptions in working memory
        workingMemory.stream()
                .filter(tP -> perceptions.containsKey(tP.getSource().getId()))
                .forEach(tP -> {
                    int id = tP.getSource().getId();
                    Perception p = perceptions.get(id);

                    tP.update(p);
                    perceptions.remove(id);
                });

//        System.out.println(being.getName() + " after -> " + perceptions);

        workingMemory.removeIf(tP -> !tP.get("exists", Boolean.class));

        for (Perception perception : perceptions.values()) {
            workingMemory.add(new TemporalPerception(perception));

            /*System.out.println("Living thing '" + this.being.getName()
                    + "' just saw perceived '" + perception.name() + "' with "
                    + perception.get("from", String.class) + ".");*/

            while (workingMemory.size() > 4) {
                workingMemory.remove(0);
            }
        }

//        System.out.println(being.getName() + being.getId() + ": " + workingMemory);

        Need mostIntenseNeed = being.getNeeds().stream()
                .sorted(Comparator.comparingDouble((Need n) -> {
                    double intensity = n.getIntensity();
                    double sources = workingMemory.stream()
                            .mapToDouble(tP -> {
                                double relevance = tP.get(n.getName(), Double.class);
                                double distance = tP.get("distance", Vector2f.class)
                                        .length() / 2;

                                return relevance * (1d / distance);
                            })
                            .sum();

                    /*System.out.println(being.getName() + ": " + n.getName() + " -> "
                            + (intensity * (1 + sources)));*/
                    return intensity * (1 + sources);
                }).reversed())
                //.sorted(Comparator.comparingDouble(Need::getIntensity).reversed())
                .map(need -> {
//                    System.out.println(being + ": " + need.getName() + " -> " + need.getIntensity());
                    return need;
                })
                .findFirst()
                .orElse(null);

        if (mostIntenseNeed != currentNeed) {
            System.out.println(being.getName() + " changed need to " + mostIntenseNeed.getName());
            being.setCurrentGoal(mostIntenseNeed.getGoal());
        }

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

            if (next == null) currentNeed = null;
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
