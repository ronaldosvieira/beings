package mind.goal;

import entity.Animal;
import mind.sense.Perception;
import mind.sense.TemporalPerception;
import org.joml.Vector2f;

import java.util.List;

public class FindFood extends MoveRandomly {
    private boolean found = false;

    public FindFood(Animal animal) {
        super(animal);
    }

    @Override
    public void cycle(List<TemporalPerception> workingMemory) {
        super.cycle(workingMemory);

        found = false;

        workingMemory.stream()
                .filter(p -> p.isA("animal"))
                .filter(p -> p.get("size", Double.class)
                        <= getAnimal().getSemantic().get("size", Double.class))
                .sorted((o1, o2) ->
                        (int) (o1.get("distance", Vector2f.class).length()
                                - o2.get("distance", Vector2f.class).length()))
                .findFirst()
                .ifPresent(this::found);
    }

    private void found(Perception perception) {
        this.perception = perception;
        this.found = true;
    }

    @Override
    public boolean check() {
        return found;
    }
}
