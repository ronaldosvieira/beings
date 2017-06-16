package mind.sense;

import entity.Animal;
import org.joml.Vector2f;

import java.util.List;
import java.util.stream.Collectors;

public class Hearing extends Sense {
    private float range;

    public Hearing(Animal being, float range) {
        super(being);

        this.range = range;
    }

    @Override
    public List<Perception> perceive() {
        Vector2f direction = ((Animal) getBeing()).getCurrentDirection();

        return getNearPerceptions(range)
                .stream()
                .map(perception -> {
                    // todo: remove slots not in need's "perceivable slots"
                    /*List<String> toRemove = perception.slots().stream()
                            .filter(s -> !s.equals("distance") && !s.equals("timestamp"))
                            .collect(Collectors.toList());
//                            .forEach(perception::removeSlot);

                    toRemove.forEach(perception::removeSlot);
                    perception.removeSlot("size");*/

                    perception.set("from", "hearing");

                    return perception;
                })
                .collect(Collectors.toList());
    }

    public static class Builder {
        private Animal animal;
        private float range = 20;

        public Builder(Animal animal) {
            this.animal = animal;
        }

        public Builder range(float range) {
            this.range = range;
            return this;
        }

        public Hearing build() {
            return new Hearing(animal, range);
        }
    }
}
