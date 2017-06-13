package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import entity.model.mind.sense.TemporalPerception;
import org.joml.Vector2f;

import java.util.List;

public class FleeFrom extends Goal {
    private double distance = 10;

    public FleeFrom(Animal animal) {
        super(animal);
    }

    public FleeFrom(Animal animal, double distance) {
        this(animal);

        this.distance = distance;
    }

    @Override
    public void cycle(List<TemporalPerception> workingMemory) {
        direction = perception
                .get("distance", Vector2f.class)
                .negate(new Vector2f())
                .normalize();
    }

    @Override
    public boolean check() {
        return perception
                .get("distance", Vector2f.class)
                .length() > this.distance;
    }
}
