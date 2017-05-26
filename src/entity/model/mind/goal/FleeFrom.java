package entity.model.mind.goal;

import entity.model.Animal;
import model.InstanceFrame;
import org.joml.Vector2f;

public class FleeFrom extends Goal {
    private InstanceFrame perception;
    private double distance = 10;

    public FleeFrom(Animal animal, InstanceFrame perception) {
        super(animal);

        this.perception = perception;
    }

    public FleeFrom(Animal animal, InstanceFrame perception, double distance) {
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
    public boolean isCompleted() {
        return perception
                .get("distance", Vector2f.class)
                .length() > this.distance;
    }
}
