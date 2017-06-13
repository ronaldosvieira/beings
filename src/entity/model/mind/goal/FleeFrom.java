package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.joml.Vector2f;

public class FleeFrom extends Goal {
    private double distance = 10;

    public FleeFrom(Animal animal, Perception perception) {
        super(animal, perception);
    }

    public FleeFrom(Animal animal, Perception perception, double distance) {
        this(animal, perception);

        this.distance = distance;
    }

    @Override
    public Vector2f getMovement(float delta) {
        return perception
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
