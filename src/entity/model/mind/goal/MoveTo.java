package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.joml.Vector2f;

public class MoveTo extends Goal {
    private Perception perception;
    private double distance = 3;

    public MoveTo(Animal animal, Perception perception) {
        super(animal);

        this.perception = perception;
        this.distance = 0.5 + (perception.get("size", Double.class) / 2)
                + (getAnimal().getSemantic().get("size", Double.class) / 2);
    }

    public MoveTo(Animal animal, Perception perception, double distance) {
        this(animal, perception);

        this.distance = distance;
    }

    @Override
    public Vector2f getMovement(float delta) {
        System.out.println(getAnimal().getName() + " move-to - " + perception.get("timestamp", Long.class));
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
