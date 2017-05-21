package entity.model;

import entity.model.mind.Mind;
import entity.model.mind.goal.Goal;
import entity.model.mind.goal.MoveRandomly;
import entity.model.util.AnimalAnim;
import io.Window;
import org.joml.Vector2f;
import render.Animation;
import render.Camera;
import world.World;

public abstract class Animal extends LivingThing {
    private Goal currentGoal;
	
	private AnimalAnim currentAnim;
	private Vector2f currentDirection;

	private Mind mind;

	private boolean isMoving;
	private float movementSpeed;

	public Animal(String name, World world, Vector2f scale, Vector2f position) {
		super(name, world, AnimalAnim.AMOUNT, scale, position);

		this.currentAnim = AnimalAnim.IDLE_N;
		this.currentDirection = new Vector2f(.0f, .1f);
		updateAnimation(this.currentDirection);

        this.mind = new Mind(this);
		
		this.movementSpeed = 5.0f; // default movement speed
		this.isMoving = false;
		
		this.currentGoal = new MoveRandomly(this);
		
		for (AnimalAnim anim : AnimalAnim.values()) {
			setAnimation(anim.index(), 
					new Animation(anim.amount(), 8,
                            "entities/" + name + "/" + anim.path()));
		}
	}

	public Vector2f getCurrentDirection() {return this.currentDirection;}

	public void setCurrentDirection(Vector2f direction) {
	    this.currentDirection = direction.normalize();
	    updateAnimation(currentDirection);
	}

	public float getMovementSpeed() {return this.movementSpeed;}
	public boolean isMoving() {return this.isMoving;}
	
	public void setMovementSpeed(float movementSpeed) {this.movementSpeed = movementSpeed;}
	
	protected void move(float delta) {move(delta, this.currentDirection);}
	
	protected void move(float delta, Vector2f direction) {
		Vector2f movement = new Vector2f();
		movement.add(movementSpeed * delta * direction.x,
				movementSpeed * delta * direction.y);
		
		this.isMoving = movementSpeed != 0;
		this.currentDirection = direction;
		
		updateAnimation(direction);
		
		super.move(movement);
	}
	
	public void updateAnimation(Vector2f direction) {
		if (movementSpeed > 0) {
			if (Math.abs(direction.x) > Math.abs(direction.y)) {
				if (direction.x > 0) selectAnimation(AnimalAnim.WALK_E);
				else selectAnimation(AnimalAnim.WALK_W);
			} else {
				if (direction.y > 0) selectAnimation(AnimalAnim.WALK_N);
				else selectAnimation(AnimalAnim.WALK_S);
			}
		} else {
			if (currentAnim.isWalking()) 
				selectAnimation(currentAnim.idle());
		}
	}
	
	public void selectAnimation(AnimalAnim anim) {
		this.currentAnim = anim;
		super.useAnimation(anim.index());
	}

    @Override
    public void cycle() {
        this.mind.update();
    }

	@Override
	public void update(float delta, Window window, Camera camera) {
		move(delta, this.currentGoal.getMovement(delta));
	}
}
