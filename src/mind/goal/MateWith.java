package mind.goal;

import entity.Animal;
import entity.LivingThing;
import mind.need.Need;
import mind.need.Reproduction;
import mind.sense.TemporalPerception;
import org.joml.Vector2f;

import java.util.List;

public class MateWith extends Goal {
    private Reproduction need;
    private Task task = new Task("reproduce");

    public MateWith(Animal animal, Reproduction need) {
        super(animal);

        this.need = need;
    }

    @Override
    public void cycle(List<TemporalPerception> workingMemory) {
        getAnimal().setMovementSpeed(0);

        if (perception == null) return;

        System.out.println(getAnimal().getName() + " mate-with - " +
                perception.get("timestamp", Long.class));

        task.execute(1);

        if (task.isCompleted()) {
            need.satisfy(need.getValue());
            getAnimal().getWorld().addEntity();
        }

        direction = perception
                .get("distance", Vector2f.class)
                .normalize(new Vector2f());
    }

    @Override
    public boolean check() {
        return task.isCompleted();
    }
}
