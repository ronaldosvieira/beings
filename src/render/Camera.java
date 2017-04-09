package render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private double width, height;
	private Vector3f position;
	private Matrix4f projection;
	
	public Camera(int width, int height) {
		this.position = new Vector3f(0, 0, 0);
		this.width = width;
		this.height = height;

		setProjection(width, height);
	}
	
	public void setProjection(int width, int height) {
		this.projection = new Matrix4f();

		setCameraBounds(width, height);
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void addPosition(Vector3f position) {
		this.position.add(position);
	}

	public void zoomIn() {
		setCameraBounds(getWidth() * .99, getHeight() * .99);
	}

	public void zoomOut() {
	    setCameraBounds(getWidth() * 1.01, getHeight() * 1.01);
    }

    private void setCameraBounds(double width, double height) {
	    this.width = width;
	    this.height = height;

	    this.projection.setOrtho2D((int) (-width / 2), (int) (width / 2),
				(int) (-height / 2), (int) (height / 2));
    }

	public double getWidth() {return this.width;}
	public double getHeight() {return this.height;}
	public Vector3f getPosition() {return this.position;}
	public Matrix4f getUntransformedProjection() {return this.projection;}
	public Matrix4f getProjection() {return projection.translate(position, new Matrix4f());}
}
