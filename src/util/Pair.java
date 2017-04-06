package util;

import com.sun.istack.internal.NotNull;

public class Pair<T, U> {
    private T first;
    private U second;

    public Pair(@NotNull T first, @NotNull U second) {
        this.first = first;
        this.second = second;
    }

    public T first() {return this.first;}
    public U second() {return this.second;}

    public Pair<U, T> reverse() {return new Pair<>(second, first);}

    @Override
    public String toString() {
        return "[" + first +  "," + second + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        return (second != null ? second.equals(pair.second) : pair.second == null);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
