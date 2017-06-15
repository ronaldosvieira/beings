package mind.goal;

import entity.Animal;
import entity.LivingThing;
import mind.sense.TemporalPerception;
import org.joml.Vector2f;

import java.util.List;

public class Attack extends Goal {
    public Attack(Animal animal) {
        super(animal);
    }

    @Override
    public void cycle(List<TemporalPerception> workingMemory) {
        getAnimal().setMovementSpeed(0);

        if (perception == null) return;

        System.out.println(getAnimal().getName() + " attack - " + perception.get("timestamp", Long.class));

        ((LivingThing) perception.getSource()).attack();

        direction = perception
                .get("distance", Vector2f.class)
                .normalize(new Vector2f());
    }

    @Override
    public boolean check() {
        System.out.println(perception.getSource().getName() + " eatable? " + perception.get("is-eatable", Boolean.class));
        System.out.println(perception.getSource().getName() + " alive? " + perception.get("is-alive", Boolean.class));
        return perception.get("is-eatable", Boolean.class);
    }
}
