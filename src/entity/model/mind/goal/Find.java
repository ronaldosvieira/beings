package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

public class Find extends Goal {
    private double distance = 3;

    public Find(Animal animal) {
        super(animal);
    }

    public Find(Animal animal, double distance) {
        this(animal);

        this.distance = distance;
    }

    @Override
    public void cycle() {
        getAnimal().setMovementSpeed(5);

        direction = perception
                .get("distance", Vector2f.class)
                .normalize(new Vector2f());
    }

    @Override
    public boolean check() {
        return perception
                .get("distance", Vector2f.class)
                .length() <= this.distance;
    }
}
