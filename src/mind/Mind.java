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
        // get perceptions from senses
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

        if (being.getWorld().checkDebugMode(1)) {
            System.out.println("perceptions of " + being.getName() + ": [");

            for (Perception p : perceptions.values()) {
                System.out.println("\t" + p);
            }

            System.out.println("]");
        }

        // todo: try to merge perceptions
        // if many of same type then merge all with distance = centroid

//        System.out.println(being.getName() + " before -> " + perceptions);

        // updates perceptions from sources already in working memory
        workingMemory.stream()
                .filter(tP -> perceptions.containsKey(tP.getSource().getId()))
                .forEach(tP -> {
                    int id = tP.getSource().getId();
                    Perception p = perceptions.get(id);

                    tP.update(p);
                    perceptions.remove(id);
                });

//        System.out.println(being.getName() + " after -> " + perceptions);

        // removes destroyed perceptions
        workingMemory.removeIf(tP -> !tP.get("exists", Boolean.class));

        // add new perceptions to working memory
        workingMemory.addAll(perceptions.values().stream()
                .map(TemporalPerception::new)
                .collect(Collectors.toList()));

        // then remove all but the 4 most relevant ones
        workingMemory = workingMemory.stream()
                .sorted(Comparator.comparingDouble((TemporalPerception tP) ->
                        being.getNeeds().stream()
                                .mapToDouble(need -> tP.get(need.getName(), Double.class))
                                .sum())
                        .reversed())
                .limit(4)
                .collect(Collectors.toList());

//        System.out.println(being.getName() + being.getId() + ": " + workingMemory);

        // find out which need demands most priority
        Need mostIntenseNeed = being.getNeeds().stream()
                .sorted(Comparator.comparingDouble((Need n) -> {
                    double intensity = n.getIntensity();
                    double sources = workingMemory.stream()
                            .mapToDouble(tP -> {
                                double relevance = tP.get(n.getName(), Double.class);
                                double distance = Math.log(tP.get("distance", Vector2f.class)
                                        .length()) + 1;

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

        // executes current goal
        Goal currentGoal = being.getCurrentGoal();
        currentGoal.cycle(workingMemory);

        // handles goal switching
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
