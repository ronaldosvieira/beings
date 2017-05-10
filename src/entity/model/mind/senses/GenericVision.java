package entity.model.mind.senses;

import entity.model.LivingThing;
import entity.model.mind.Sense;
import model.InstanceFrame;

import java.util.List;

public class GenericVision extends Sense {
    public GenericVision(LivingThing being) {
        super(being);
    }

    @Override
    public List<InstanceFrame> perceive() {
       return getBeing().getWorld().getNearEntities(getBeing(), 10);
    }
}
