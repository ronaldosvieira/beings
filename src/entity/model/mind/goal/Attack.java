package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.joml.Vector2f;

import java.util.Random;

public class Attack extends Goal {
    private Perception perception;

    public Attack(Animal animal, Perception perception) {
        super(animal);

        this.perception = perception;
        this.addPreReq(new MoveTo(animal, perception));
    }

    @Override
    public Vector2f getMovement(float delta) {
        Random random = new Random();

        getAnimal().setMovementSpeed(0);
        System.out.println(getAnimal().getName() + " attack - " + perception.get("timestamp", Long.class));

        return perception
                .get("distance", Vector2f.class)
                .normalize(new Vector2f());
    }

    @Override
    public boolean isCompleted() {
        return !perception.get("is-alive", Boolean.class);
    }
}
