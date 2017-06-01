package frames.constraint;

import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.function.BiPredicate;

public class RangeConstraint implements Constraint {
    private final String constraint = "range";
    private Comparable lo, hi;
    private char[] bounds = {'[', ')'};

    private static final HashMap<Character, BiPredicate<Comparable, Comparable>> operators = new HashMap<>();
    static {
        operators.put(')', (c1, c2) -> c1.compareTo(c2) < 0);
        operators.put(']', (c1, c2) -> c1.compareTo(c2) <= 0);
        operators.put('(', (c1, c2) -> c1.compareTo(c2) > 0);
        operators.put('[', (c1, c2) -> c1.compareTo(c2) >= 0);
    }

    public RangeConstraint(Comparable lo, Comparable hi) {
        this(lo, hi, "[]");
    }

    @Contract("_, _, null -> fail")
    public RangeConstraint(Comparable lo, Comparable hi, @NotNull String inclusivity) {
        if (lo == null && hi == null)
            throw new IllegalArgumentException("Lo and hi can't both be null");

        this.lo = lo;
        this.hi = hi;

        if (inclusivity == null || inclusivity.length() != 2)
            throw new IllegalArgumentException("Invalid inclusivity string");

        char incl = inclusivity.charAt(0);

        if (incl == '[') bounds[0] = '[';
        else if (incl == '(' || incl == ']') bounds[0] = '(';
        else throw new IllegalArgumentException("Invalid inclusivity string");

        incl = inclusivity.charAt(1);

        if (incl == ']') bounds[1] = ']';
        else if (incl == ')' || incl == '[') bounds[1] = ')';
        else throw new IllegalArgumentException("Invalid inclusivity string");
    }

    @Override
    public boolean check(Object value) {
        if (!(value instanceof Comparable)) return false;

        try {
            lo.getClass().cast(value);
            hi.getClass().cast(value);
        } catch (ClassCastException e) {
            return false;
        }

        return operators.get(bounds[0]).test((Comparable) value, lo)
                && operators.get(bounds[1]).test((Comparable)value, hi);
    }
}
