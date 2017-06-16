package mind.goal;

import entity.Animal;
import mind.sense.TemporalPerception;
import org.joml.Vector2f;

import java.util.List;

public class FleeFrom extends Goal {
    private double distance = 15;

    public FleeFrom(Animal animal) {
        super(animal);
    }

    public FleeFrom(Animal animal, double distance) {
        this(animal);

        this.distance = distance;
    }

    @Override
    public void cycle(List<TemporalPerception> workingMemory) {
        if (perception == null) return;

        getAnimal().setMovementSpeed(5);

        direction = perception
                .get("distance", Vector2f.class)
                .negate(new Vector2f())
                .normalize();
    }

    @Override
    public boolean check() {
        /*System.out.println(getAnimal().getName() + " flee-from - "
                + perception + " "
                + perception.get("timestamp", Long.class) + " "
                + perception.get("distance", Vector2f.class).length() + " "
                + perception.get("from", String.class) + " ");*/
        return perception
                .get("distance", Vector2f.class)
                .length() > this.distance;
    }
}
