package model.constraint;

import com.sun.istack.internal.NotNull;

public class TypeConstraint implements Constraint {
    private final String constraint = "type";
    private Class<?> type;

    public TypeConstraint(@NotNull Class<?> type) {
        if (type == null)
            throw new IllegalArgumentException("TypeConstraint with null class");

        this.type = type;
    }

    @Override
    public boolean check(Object value) {
        return type.isAssignableFrom(value.getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeConstraint)) return false;

        TypeConstraint that = (TypeConstraint) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
