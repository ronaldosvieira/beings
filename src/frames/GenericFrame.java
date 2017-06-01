package model;

public class GenericFrame extends Frame {
	public GenericFrame(String name) {
		super(name);
	}
	
	public GenericFrame(String name, GenericFrame parent) {
		super(name, parent);
	}

	public GenericFrame(GenericFrame frame) {super(frame);}

	public GenericFrame clone() {return new GenericFrame(this);}
}
