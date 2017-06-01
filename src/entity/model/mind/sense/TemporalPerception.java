package entity.model.mind.sense;

public class TemporalPerception extends Perception {
    private TemporalPerception next;

    public TemporalPerception(Perception perception) {
        super(perception);

        this.next = null;
    }

    public void update(TemporalPerception next) {
        this.next = (TemporalPerception) this.clone();
        this.copy(next);
    }

    public TemporalPerception next() {return this.next;}
}
