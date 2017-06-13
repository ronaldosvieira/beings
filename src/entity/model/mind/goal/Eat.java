package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.joml.Vector2f;

import java.util.Random;

public class Eat extends Goal {
    public Eat(Animal animal) {
        super(animal);
    }

    @Override
    public Vector2f getMovement(float delta) {
        getAnimal().setMovementSpeed(0);

        System.out.println(getAnimal().getName() + " eat - " + perception.get("timestamp", Long.class));

        return perception
                .get("distance", Vector2f.class)
                .normalize(new Vector2f());
    }

    @Override
    public boolean check() {
        return !perception.get("is-alive", Boolean.class);
    }
}
