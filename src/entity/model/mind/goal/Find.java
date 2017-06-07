package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

public class Find extends Goal {
    private double distance = 3;

    public Find(Animal animal, Perception perception) {
        super(animal, perception);

        this.distance = 1.5 + (perception.get("size", Double.class) / 2)
                + (getAnimal().getSemantic().get("size", Double.class) / 2);
    }

    public Find(Animal animal, Perception perception, double distance) {
        this(animal, perception);

        this.distance = distance;
    }

    @Override
    public Vector2f getMovement(float delta) {
        getAnimal().setMovementSpeed(5);

        return perception
                .get("distance", Vector2f.class)
                .normalize(new Vector2f());
    }

    @Override
    public boolean isCompleted() {
        return perception
                .get("distance", Vector2f.class)
                .length() <= this.distance;
    }
}
