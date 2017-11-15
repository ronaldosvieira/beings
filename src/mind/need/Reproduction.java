package mind.need;

import entity.Animal;
import mind.goal.*;
import mind.sense.Perception;

public class Reproduction extends Need {
    public Reproduction(Animal animal, double decayPerMinute, double value) {
        super(animal, decayPerMinute, value);
    }

    public Reproduction(Animal animal, double decayRate) {
        super(animal, decayRate);
    }

    @Override
    public String getName() {return "reproduction";}

    @Override
    public Goal getGoal() {
        return new GoalChain(new FindPartner(getAnimal()))
                .then(new MoveTo(getAnimal()))
                .then(new MateWith(getAnimal(), this))
                .get();
    }

    @Override
    public double evaluate(Perception perception) {
        return perception.isA(getAnimal().getSemantic().name())?
                1 : 0;
    }
}
