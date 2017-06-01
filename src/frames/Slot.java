package model;

import model.constraint.Constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Slot {
    private Object value;
    private Consumer<Object> if_added;
    private Supplier<Object> if_needed;
    private List<Constraint> constraints;

    public Slot() {
        this.constraints = new ArrayList<>();
    }

    public Slot(Object value) {
        this();
        this.value = value;
    }

    public Slot(Slot slot) {
        this.value = slot.value;
        this.if_added = slot.if_added;
        this.if_needed = slot.if_needed;
        this.constraints = slot.constraints;
    }

    public Object getValue() {
        if (value != null)
            return value;
        else if (if_needed != null)
            return if_needed.get();
        else
            return null;
    }

    public void setValue(Object value) throws IllegalArgumentException {
        boolean fails = constraints.parallelStream()
                .anyMatch(constraint -> !constraint.check(value));

        if (fails) throw new IllegalArgumentException("Value '" + value + "' fails a constraint check.");

        if (if_added != null) if_added.accept(value);

        this.value = value;
    }

    public void setIfAdded(Consumer<Object> if_added) {this.if_added = if_added;}
    public void setIfNeeded(Supplier<Object> if_needed) {this.if_needed = if_needed;}

    public void clearConstraints() {this.constraints.clear();}

    public void addConstraint(Constraint constraint) {
        if (!this.constraints.contains(constraint)) {
            this.constraints.add(constraint);
        }
    }

    public boolean hasValue() {return this.value != null;}
}
