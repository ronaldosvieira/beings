package model;

import java.util.concurrent.atomic.AtomicInteger;

public class InstanceFrame extends Frame {
	private static AtomicInteger nextId = new AtomicInteger();

	private int id;

	public InstanceFrame(String name, GenericFrame parent) {
		super(name, parent);
	}

    public int id() {return this.id;}

    @Override
    public String name() {
        return super.name() + this.id();
    }

    public InstanceFrame(InstanceFrame frame) {super(frame);}

	public InstanceFrame clone() {return new InstanceFrame(this);}
}
