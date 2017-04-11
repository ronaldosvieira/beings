package render;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private double width, height;
    private double zoom;
	private Vector3f position;
	private Matrix4f projection;
	
	public Camera(int width, int height) {
		this.position = new Vector3f(0, 0, 0);
		this.width = width;
		this.height = height;
		this.zoom = 1;

		setProjection();
	}
	
	public void setProjection() {
		this.projection = new Matrix4f();

		updateCameraBounds();
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void addPosition(Vector3f position) {
		this.position.add(position);
	}

	public void zoomIn() {
	    this.zoom *= 0.99;
		updateCameraBounds();
	}

	public void zoomOut() {
	    this.zoom *= 1.01;
	    updateCameraBounds();
    }

    private void updateCameraBounds() {
	    this.projection.setOrtho2D((int) ((-width * zoom) / 2), (int) ((width * zoom) / 2),
				(int) ((-height * zoom) / 2), (int) ((height * zoom) / 2));
    }

    public void setSize(double width, double height) {
	    this.width = width;
	    this.height = height;

	    updateCameraBounds();
    }

    public void setZoom(double zoom) {
	    this.zoom = zoom;

	    updateCameraBounds();
    }

	public double getWidth() {return this.width;}
	public double getHeight() {return this.height;}
	public double getZoom() {return this.zoom;}
	public Vector3f getPosition() {return this.position;}
	public Matrix4f getUntransformedProjection() {return this.projection;}
	public Matrix4f getProjection() {return projection.translate(position, new Matrix4f());}
}
