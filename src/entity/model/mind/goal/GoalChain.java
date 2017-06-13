package entity.model.mind.goal;

import entity.model.mind.sense.Perception;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class GoalChain {
    private LinkedList<Goal> chain = new LinkedList<>();
    private Perception input;

    public GoalChain(@NotNull Goal first) {
        this.chain.add(first);
    }

    public GoalChain input(@NotNull Perception perception) {
        this.input = perception;

        return this;
    }

    public GoalChain then(@NotNull Goal next) {
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
