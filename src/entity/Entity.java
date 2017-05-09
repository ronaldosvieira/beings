package entity;

import assets.Assets;
import collision.AABB;
import collision.Collision;
import game.Game;
import game.Shader;
import io.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import render.Animation;
import render.Camera;
import world.World;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Entity {
    private static AtomicInteger nextId = new AtomicInteger();

    private int id;
	protected AABB boundingBox;
	//private Texture texture;
	protected Animation[] animations;
	private int useAnimation;
	protected Transform transform;
	
	private boolean isSolid;
	private boolean isWalkable;
	
	public Entity(int maxAnimations, Vector2f scale, Vector2f position) {
	    this.id = nextId.incrementAndGet();

		this.animations = new Animation[maxAnimations];
        this.useAnimation = 0;

        this.transform = new Transform();
        this.transform.pos.set(position.x, position.y, 0).mul(2);
        this.transform.scale.set(scale.x, scale.y, 1);

		this.isSolid = false;
		
		boundingBox = new AABB(
				// TODO: find a better box placement
				new Vector2f(transform.pos.x, transform.pos.y + 1/4),
				new Vector2f(transform.scale.x, transform.scale.y / 2));
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
	public abstract void cycle();
	
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getProjection();
		target.mul(world.getWorldMatrix());
		
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		animations[useAnimation].bind(0);

        Assets.getModel().render();
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
	
	public void collideWithEntities(Entity entity) {
		Collision collision = boundingBox.getCollision(entity.boundingBox);

		if (this == entity) return;
		if (this.isWalkable() ^ entity.isWalkable()) return;
		
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

	public int getId() {return this.id;}

	public Vector2f getScale() {
	    return new Vector2f(this.transform.scale.x,
                this.transform.scale.y);
	}

	public Vector2f getPosition() {
	    return new Vector2f(this.transform.pos.x,
                this.transform.pos.y).mul(.5f);
    }

    public void setPosition(Vector2f position) {
		this.transform.pos = new Vector3f(position, 0).mul(2);
	}

    public AABB getBoundingBox() {return this.boundingBox;}

	public boolean isSolid() {return this.isSolid;}
	public boolean isWalkable() {return this.isWalkable;}
	
	public Entity setSolid(boolean isSolid) {
		this.isSolid = isSolid;
		return this;
	}
	
	public Entity setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
		return this;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
