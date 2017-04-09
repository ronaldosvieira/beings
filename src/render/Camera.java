package render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private int width, height;
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
		setCameraBounds(getWidth() - 5, getHeight() - 5);
	}

	public void zoomOut() {
	    setCameraBounds(getWidth() + 5, getHeight() + 5);
    }

    private void setCameraBounds(int width, int height) {
	    this.width = width;
	    this.height = height;

	    this.projection.setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);
    }

	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public Vector3f getPosition() {return this.position;}
	public Matrix4f getUntransformedProjection() {return this.projection;}
	public Matrix4f getProjection() {return projection.translate(position, new Matrix4f());}
}
