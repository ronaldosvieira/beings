package frames;

import frames.constraint.Constraint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Slot {
    private Frame frame;
    private Object value;
    private Consumer<Object> if_added;
    private Function<Frame, Object> if_needed;
    private List<Constraint> constraints;

    public Slot(Frame frame) {
        this.frame = frame;
        this.constraints = new ArrayList<>();
    }

    public Slot(Frame frame, Object value) {
        this(frame);
        this.value = value;
    }

    public Slot(Frame frame, Slot slot) {
        this.frame = frame;
        this.value = deepClone(slot.value);
        this.if_added = slot.if_added;
        this.if_needed = slot.if_needed;
        this.constraints = slot.constraints;
    }

    public Object getValue() {
        if (value != null)
            return value;
        else if (if_needed != null)
            return if_needed.apply(frame);
        else
            return null;
    }

    public void setValue(Object value) throws IllegalArgumentException {
        boolean fails = constraints.stream()
                .anyMatch(constraint -> !constraint.check(value));

        if (fails) throw new IllegalArgumentException("Value '" + value + "' fails a constraint check.");

        if (if_added != null) if_added.accept(value);

        this.value = value;
    }

    public void setIfAdded(Consumer<Object> if_added) {this.if_added = if_added;}
    public void setIfNeeded(Function<Frame, Object> if_needed) {this.if_needed = if_needed;}

    public void clearConstraints() {this.constraints.clear();}

    public void addConstraint(Constraint constraint) {
        if (!this.constraints.contains(constraint)) {
            this.constraints.add(constraint);
        }
    }

    public boolean hasValue() {return this.value != null;}

    public Frame getFrame() {return this.frame;}

    public Slot merge(Slot slot) {
        Slot res = new Slot(frame, slot);

        if (value != null) res.setValue(value);
        if (if_added != null) res.setIfAdded(if_added);
        if (if_needed != null) res.setIfNeeded(if_needed);

        for (Constraint constr : constraints) {
            res.addConstraint(constr);
        }

        return res;
    }

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
