package entity.model.mind.goal;

import entity.model.mind.sense.Perception;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class GoalBuilder {
    private LinkedList<Goal> chain = new LinkedList<>();
    private Perception input;

    public GoalBuilder(@NotNull Goal first) {
        this.chain.add(first);
    }

    public GoalBuilder input(@NotNull Perception perception) {
        this.input = perception;

        return this;
    }

    public GoalBuilder then(@NotNull Goal next) {
        this.chain.add(next);

        return this;
    }

    public Goal get() {
        Goal output = chain.element();

        for (int i = 1; i < chain.size(); i++) {
            Goal next = chain.get(i);
            next.addPreReq(output);

            output = next;
        }

        output.input(input);

        return output;
    }
}
