package entity.model.mind.sense;

import entity.model.Animal;
import entity.model.Thing;
import org.joml.Vector2f;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Sense {
    private Animal being;

    public Sense(Animal being) {
        this.being = being;
    }

    abstract public List<Perception> perceive();

    public Animal getBeing() {return this.being;}

    public List<Perception> getNearPerceptions(float range) {
        return getBeing().getWorld().getNearEntities(getBeing(), range)
                .stream()
                .map(entity1 -> (Thing) entity1)
                .map(thing -> new Perception(getBeing(), thing))
                .collect(Collectors.toList());
    }
}
