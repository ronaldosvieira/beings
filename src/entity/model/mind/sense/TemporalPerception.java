package entity.model.mind.sense;

public class TemporalPerception extends Perception {
    private TemporalPerception next;
    private static int MAX_TP_LENGTH = 5;

    public TemporalPerception(Perception perception) {
        super(perception);

        this.next = null;
    }

    public TemporalPerception(TemporalPerception tempPerception) {
        super(tempPerception);

        this.next = tempPerception.next;
    }

    public void update(Perception next) {
        this.next = new TemporalPerception(this);
        copy(next);

        prune();
    }

    private void prune() {
        TemporalPerception temp = this.next;

        for (int i = 1;
             i < MAX_TP_LENGTH - 1 && temp.next() != null;
             i++, temp = temp.next());

        temp.next = null;
    }

    @Override
    public String toString() {
        return getSource().getName() + getSource().getId();
    }

    public TemporalPerception next() {return this.next;}
}
