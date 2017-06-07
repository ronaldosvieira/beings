package entity.model.mind.goal;

import entity.model.mind.sense.Perception;

import java.util.LinkedList;

public class GoalBuilder {
    private LinkedList<Goal> chain = new LinkedList<>();
    private Perception input;

    public GoalBuilder(Goal first) {
        this.chain.add(first);
    }

    public GoalBuilder input(Perception perception) {
        this.input = perception;

        return this;
    }

    public GoalBuilder then(Goal next) {
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
