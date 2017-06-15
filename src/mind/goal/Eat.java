package mind.goal;

import entity.Animal;
import entity.LivingThing;
import mind.sense.TemporalPerception;
import org.joml.Vector2f;

import java.util.List;

public class Eat extends Goal {
    public Eat(Animal animal) {
        super(animal);
    }

    @Override
    public void cycle(List<TemporalPerception> workingMemory) {
        getAnimal().setMovementSpeed(0);

        if (perception == null) return;

        System.out.println(getAnimal().getName() + " eat - " + perception.get("timestamp", Long.class));

        perception.getSource().destroy();

        direction = perception
                .get("distance", Vector2f.class)
                .normalize(new Vector2f());
    }

    @Override
    public boolean check() {
        return perception.get("is-eatable", Boolean.class);
    }
}
