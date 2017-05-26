package entity.model.mind.goal;

import entity.model.Animal;
import org.joml.Vector2f;

public abstract class Goal {
    private Animal animal;

    public Goal(Animal animal) {
        this.animal = animal;
    }

    public Animal getAnimal() {return this.animal;}

    public abstract Vector2f getMovement(float delta);

    public abstract boolean isCompleted();
}
