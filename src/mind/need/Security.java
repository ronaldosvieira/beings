package mind.need;

import entity.Animal;
import mind.goal.*;

public class Security extends Need {
    public Security(Animal animal) {
        super(animal, 0d, .6d);
    }

    @Override
    public Goal getGoal() {
        return new GoalChain(new FindThreat(getAnimal()))
                .then(new FleeFrom(getAnimal()))
                .get();
    }
}
