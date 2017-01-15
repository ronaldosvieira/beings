package entity;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import collision.Collision;
import game.Shader;
import io.Window;
import render.Animation;
import render.Camera;
import render.Model;
import world.World;

public abstract class Entity {
	protected static Model model;
	protected AABB boundingBox;
	//private Texture texture;
	protected Animation[] animations;
	private int useAnimation;
	protected Transform transform;
	
	private boolean isSolid;
	
	public Entity(int maxAnimations, Transform transform) {
		this.animations = new Animation[maxAnimations];
        this.useAnimation = 0;

        this.transform = new Transform();
        this.transform.pos.set(transform.pos.x,
                transform.pos.y, 0);
        this.transform.scale.set(transform.scale.x, transform.scale.y, transform.scale.z);

		this.isSolid = false;
		
		boundingBox = new AABB(
				new Vector2f(transform.pos.x, transform.pos.y),
				new Vector2f(transform.scale.x, transform.scale.y));
	}
	
	protected void setAnimation(int index, Animation animation) {
		animations[index] = animation;
	}
	
	public void useAnimation(int index) {
		this.useAnimation = index;
	}
	
	public void move(Vector2f direction) {
		transform.pos.add(new Vector3f(direction, 0));
		
		boundingBox.getCenter().set(transform.pos.x, transform.pos.y);
	}
	
	public abstract void update(float delta, Window window, Camera camera, World world);
	
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getProjection();
		target.mul(world.getWorldMatrix());
		
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		animations[useAnimation].bind(0);
		
		model.render();
	}
	
	public static void initAsset() {
		float[] vertices = new float[] {
				-1f, 1f, 0, // 0
				1f, 1f, 0,  // 1
				1f, -1f, 0, // 2
				-1f, -1f, 0,// 3
			};
			
		float[] texture = new float[] {0,0, 1,0, 1,1, 0,1};
		int[] indexes = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		model = new Model(vertices, texture, indexes);
	}
	
	public static void deleteAsset() {
		model = null;
	}

	public void collideWithTiles(World world) {
		AABB[] boxes = new AABB[25];
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boxes[i + j * 5] = world.getTileBoundingBox(
						(int) (((transform.pos.x / 2) + 0.5f) - (5 / 2)) + i,
						(int) (((-transform.pos.y / 2) + 0.5f) - (5 / 2)) + j);
			}
		}
		
		AABB box = null;
		
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] != null) {
				if (box == null) box = boxes[i];
				
				Vector2f length = box.getCenter()
						.sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f length2 = boxes[i].getCenter()
						.sub(transform.pos.x, transform.pos.y, new Vector2f());
				
				if (length.lengthSquared() > length2.lengthSquared()) {
					box = boxes[i];
				}
			}
		}
		
		if (box != null) {
			Collision data = boundingBox.getCollision(box);
			if (data.isIntersecting) {
				boundingBox.correctPosition(box, data);
				transform.pos.set(boundingBox.getCenter(), 0);
			}
			
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) {
					if (box == null) box = boxes[i];
					
					Vector2f length = box.getCenter()
							.sub(transform.pos.x, transform.pos.y, new Vector2f());
					Vector2f length2 = boxes[i].getCenter()
							.sub(transform.pos.x, transform.pos.y, new Vector2f());
					
					if (length.lengthSquared() > length2.lengthSquared()) {
						box = boxes[i];
					}
				}
			}
			
			data = boundingBox.getCollision(box);
			if (data.isIntersecting) {
				boundingBox.correctPosition(box, data);
				transform.pos.set(boundingBox.getCenter(), 0);
			}
		}
	}
	
	public void collideWithEntitiy(Entity entity) {
		Collision collision = boundingBox.getCollision(entity.boundingBox);
		
		if (collision.isIntersecting) {
			if (entity.isSolid()) {
				boundingBox.correctPosition(entity.boundingBox, collision);
				transform.pos.set(boundingBox.getCenter().x, boundingBox.getCenter().y, 0);
			} else {
				collision.distance.x /= 2;
				collision.distance.y /= 2;
				
				boundingBox.correctPosition(entity.boundingBox, collision);
				transform.pos.set(boundingBox.getCenter().x, boundingBox.getCenter().y, 0);
			
				entity.boundingBox.correctPosition(boundingBox, collision);
				entity.transform.pos.set(entity.boundingBox.getCenter().x, 
						entity.boundingBox.getCenter().y, 0);
			}
		}
	}
	
	public boolean isSolid() {return this.isSolid;}
	
	public Entity setSolid(boolean isSolid) {
		this.isSolid = isSolid;
		return this;
	}
}
