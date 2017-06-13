package frames;

import java.util.concurrent.atomic.AtomicInteger;

public class InstanceFrame extends Frame {
	private static AtomicInteger nextId = new AtomicInteger();

	private int id;

	public InstanceFrame(String name, GenericFrame parent) {
		super(name, parent);

		for (Frame i = parent(); i != null; i = i.parent()) {
		    i.slots.forEach((key, slot) -> {
                if (slots.containsKey(key)) slot = slots.get(key).merge(slot);

                slots.put(key, slot);
            });
        }
    }

    public int id() {return this.id;}

    @Override
    public String name() {
        return super.name() + this.id();
    }

    public InstanceFrame(InstanceFrame frame) {super(frame);}

	public InstanceFrame clone() {return new InstanceFrame(this);}
}
