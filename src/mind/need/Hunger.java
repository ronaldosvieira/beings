package mind.need;

import entity.Animal;
import mind.goal.*;
import mind.sense.Perception;

public class Hunger extends Need {
    public Hunger(Animal animal, double decayPerMinute, double value) {
        super(animal, decayPerMinute, value);
    }

    public Hunger(Animal animal, double decayRate) {
        super(animal, decayRate);
    }

    @Override
    public String getName() {return "hunger";}

    @Override
    public void decay(float delta) {
        super.decay(delta);

        if (getValue() >= 1.0) getAnimal().attack();
    }

    @Override
    public Goal getGoal() {
        return new GoalChain(new FindFood(getAnimal()))
                .then(new MoveTo(getAnimal()))
                .then(new Attack(getAnimal()))
                .then(new Eat(getAnimal()))
                .get();
    }

    @Override
    public double evaluate(Perception perception) {
        return 0;
    }
}
