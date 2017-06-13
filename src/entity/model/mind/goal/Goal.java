package entity.model.mind.goal;

import entity.model.Animal;
import entity.model.mind.sense.Perception;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Goal {
    private Animal animal;
    private List<Goal> preReqs = new ArrayList<>();
    private Goal next;

    protected Perception perception;

    public Goal(Animal animal, Perception perception) {
        this.animal = animal;
        this.perception = perception;
    }

    public Animal getAnimal() {return this.animal;}

    public abstract Vector2f getMovement(float delta);

    public List<Goal> preReqs() {
        return this.preReqs.stream()
                .filter(goal -> !goal.isCompleted())
                .collect(Collectors.toList());
    }

    protected void addPreReq(Goal preReq) {this.preReqs.add(preReq);}

    public final boolean isCompleted() {
        return this.preReqs.stream().allMatch(Goal::isCompleted) && check();
    }

    abstract public boolean check();

    public Goal input(Perception perception) {
        this.perception = perception;

        return this;
    }

    public Perception output() {return this.perception;}

    public void next(Goal next) {this.next = next;}
    public Goal next() {return this.next;}
}
