package mind.sense;

import entity.Animal;
import entity.Thing;
import frames.InstanceFrame;
import org.joml.Vector2f;

public class Perception extends InstanceFrame {
    private Thing source;

    public Perception(Animal subject, Thing source) {
        super(source.getSemantic());

        this.source = source;

        Vector2f pos1 = source.getPosition();
        Vector2f pos2 = subject.getPosition();

        this.set("distance", pos1.sub(pos2, new Vector2f()));
        this.set("timestamp", System.currentTimeMillis());
    }

    public Thing getSource() {return this.source;}

    public Perception(Perception perception) {
        super(perception);

        this.source = perception.source;
    }

    @Override
    public Perception clone() {
        return new Perception(this);
    }

    public boolean isSameSource(Perception perception) {
        // todo: calc precisely (use mind frequency?)
        // return this.get("distance", Vector2f.class).length() < 1;
        return this.getSource().getId() == perception.getSource().getId();
    }

    public Perception merge(Perception other) {
        Perception res = new Perception(other);

        res.slots.putAll(this.slots);

        return res;
    }

    public static Perception combine(Perception first, Perception second) {
        return first.merge(second);
    }
}
