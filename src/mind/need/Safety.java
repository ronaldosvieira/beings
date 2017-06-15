package mind.need;

import entity.Animal;
import mind.goal.*;

public class Safety extends Need {
    public Safety(Animal animal) {
        super(animal, 0d, .1d);
    }

    @Override
    public String getName() {return "safety";}

    @Override
    public Goal getGoal() {
        return new GoalChain(new FindThreat(getAnimal()))
                .then(new FleeFrom(getAnimal()))
                .get();
    }
}
