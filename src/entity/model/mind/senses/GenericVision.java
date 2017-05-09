package entity.model.mind.senses;

import collision.AABB;
import entity.Entity;
import entity.model.LivingThing;
import entity.model.Thing;
import entity.model.mind.Sense;
import game.Game;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenericVision extends Sense {
    public GenericVision(LivingThing being) {
        super(being);
    }

    @Override
    public List<Thing> perceive() {
        List<Thing> perceptions = new ArrayList<>();
        Vector2f position = this.getBeing().getPosition();
        List<Entity> entities = this.getBeing()
                .getWorld()
                .getNearEntities(new AABB(position, new Vector2f(10, 10)))
                .stream()
                .filter(entity -> !entity.equals(this.getBeing()))
                .collect(Collectors.toList());

        // todo: implement a less naive version
        for (Entity entity : entities) {
            float dist = entity.getPosition().distance(position);

            if (dist <= 10) perceptions.add((Thing) entity);
        }

        return perceptions;
    }
}
