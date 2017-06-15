package mind.goal;

import mind.sense.Perception;
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
        Goal goal = this.chain.element().input(input);

        for (int i = 1; i < this.chain.size(); i++) {
            Goal next = this.chain.get(i).input(input);
            next.addPreReq(goal);

            goal = next;
        }

        System.out.println("goal chain built: " + goal);

        return goal;
    }
}
