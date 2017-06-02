package entity.model.mind.sense;

public class TemporalPerception extends Perception {
    private TemporalPerception next;

    public TemporalPerception(Perception perception) {
        super(perception);

        this.next = null;
    }

    public void update(Perception next) {
        this.next = new TemporalPerception(this);
        this.copy(next);
    }

    public TemporalPerception next() {return this.next;}
}
