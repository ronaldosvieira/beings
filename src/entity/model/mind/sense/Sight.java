package entity.model.mind.sense;

import entity.model.Animal;
import model.InstanceFrame;
import org.joml.Vector2f;

import java.util.List;
import java.util.stream.Collectors;

public class Sight extends Sense {
    private float range;
    private float angle;

    public Sight(Animal being, float range, float angle) {
        super(being);

        this.range = range;
        this.angle = (float) Math.toRadians(angle / 2f);
    }

    @Override
    public List<InstanceFrame> perceive() {
        Vector2f direction = ((Animal) getBeing()).getCurrentDirection();

        return getBeing().getWorld()
                .getNearEntities(getBeing(), range)
                .stream()
                .filter(perception -> {
                    Vector2f distance = (Vector2f) perception.get("distance");
                    double angle = Math.acos(direction.dot(distance.normalize(new Vector2f())));

                    return angle <= this.angle;
                })
                .collect(Collectors.toList());
    }

    public static class SightBuilder {
        private Animal animal;
        private float range = 10;
        private float angle = 120;

        public SightBuilder(Animal animal) {
            this.animal = animal;
        }

        public SightBuilder range(float range) {
            this.range = range;
            return this;
        }

        public SightBuilder angle(float angle) {
            this.angle = angle;
            return this;
        }

        public Sight build() {
            return new Sight(animal, range, angle);
        }
    }
}
