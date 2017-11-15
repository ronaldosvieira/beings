package mind.need;

import entity.Animal;
import mind.goal.Goal;
import mind.sense.Perception;

public abstract class Need {
    private Animal animal;
    private double value;
    private double decayRate;

    public Need(Animal animal, double decayPerMinute, double value) {
        this.animal = animal;
        this.value = value;
        this.decayRate = decayPerMinute / 60;
    }

    public Need(Animal animal, double decayRate) {this(animal, decayRate, 0.5);}

    public double getValue() {return value;}

    public Animal getAnimal() {return animal;}

    public abstract String getName();

    public void decay(float delta) {
        value = Math.min(value + decayRate * delta, 1.0);
    }

    public void satisfy(double amount) {
        this.value = Math.max(0d, Math.min(1d, value - amount));
    }

    public double getIntensity() {return value;/*
        double normalizedValue = 10 * (value - 0.5);

        return (1 / (1 + Math.pow(Math.E,(-1 * normalizedValue))));*/
    }

    public abstract Goal getGoal();

    public abstract double evaluate(Perception perception);
}
