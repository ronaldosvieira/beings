package mind.need;

import entity.Animal;
import mind.goal.*;
import mind.sense.Perception;

public class Safety extends Need {
    public Safety(Animal animal) {
        super(animal, 0d, .3d);
    }

    @Override
    public String getName() {return "safety";}

    @Override
    public Goal getGoal() {
        return new GoalChain(new FindThreat(getAnimal()))
                .then(new FleeFrom(getAnimal()))
                .get();
    }

    @Override
    public double evaluate(Perception perception) {
        return perception.isA("animal")
                && perception.get("size", Double.class)
                > getAnimal().getSemantic().get("size", Double.class)?
                1 : 0;
    }
}
