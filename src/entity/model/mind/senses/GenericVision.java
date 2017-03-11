package entity.model.mind.senses;

import entity.Entity;
import entity.model.LivingThing;
import entity.model.Thing;
import entity.model.mind.Sense;
import game.Game;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GenericVision extends Sense {
    public GenericVision(LivingThing being) {
        super(being);
    }

    @Override
    public List<Thing> perceive() {
        List<Thing> perceptions = new ArrayList<>();
        Vector2f position = this.getBeing().getPosition();
        List<Entity> entities = Game.getInstance().getWorld().getEntities();

        // todo: implement a less naive version
        for (Entity entity : entities) {
            float dist = entity.getPosition().distance(position);

            if (dist <= 10) perceptions.add((Thing) entity);
        }

        return perceptions;
    }
}
