package frames.constraint;

import com.sun.istack.internal.NotNull;

public class TypeConstraint implements Constraint {
    private final String type = "type";
    private Class<?> className;

    public TypeConstraint(@NotNull Class<?> className) {
        if (className == null)
            throw new IllegalArgumentException("TypeConstraint with null class");

        this.className = className;
    }

    @Override
    public boolean check(Object value) {
        return className.isAssignableFrom(value.getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeConstraint)) return false;

        TypeConstraint that = (TypeConstraint) o;

        return className.equals(that.className);
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }
}
