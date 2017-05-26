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
                .map(thing -> {
                    Perception perception = new Perception(thing.getSemantic());
                    Vector2f pos1 = thing.getPosition();
                    Vector2f pos2 = getBeing().getPosition();

                    perception.set("distance",
                            pos1.sub(pos2, new Vector2f()));

                    return perception;
                })
                .collect(Collectors.toList());
    }
}
