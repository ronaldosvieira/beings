package model.constraint;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class ContainsConstraint implements Constraint {
    private final String constraint = "contains";
    private List accepts;

    public ContainsConstraint(@NotNull List accepts) {
        if (accepts == null)
            throw new IllegalArgumentException("ContainsConstraint with null list");

        this.accepts = accepts;
    }

    @Override
    public boolean check(Object value) {
        return accepts.contains(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContainsConstraint)) return false;

        ContainsConstraint that = (ContainsConstraint) o;

        return accepts.equals(that.accepts);
    }

    @Override
    public int hashCode() {
        return accepts.hashCode();
    }
}
