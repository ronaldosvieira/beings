package entity.model.mind.senses;

import entity.model.Animal;
import entity.model.mind.Sense;
import model.Frame;
import model.InstanceFrame;
import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class GenericSight extends Sense {
    private double angle;

    public GenericSight(Animal being, int angle) {
        super(being);

        this.angle = Math.toRadians(angle / 2f);
    }

    @Override
    public List<InstanceFrame> perceive() {
        Vector2f direction = ((Animal) getBeing()).getCurrentDirection();

        return getBeing().getWorld()
                .getNearEntities(getBeing(), 10)
                .stream()
                .filter(perception -> {
                    Vector2f distance = (Vector2f) perception.get("distance");
                    double angle = Math.acos(direction.dot(distance.normalize(new Vector2f())));

                    /*System.out.println("Entity " + getBeing() + ":");
                    System.out.println(perception.getName() + " is in fov?");
                    System.out.println("angle -> " + Math.toDegrees(angle));
                    System.out.println(angle <= this.angle? "Yes" : "No");*/

                    return angle <= this.angle;
                })
                .collect(Collectors.toList());
    }
}
