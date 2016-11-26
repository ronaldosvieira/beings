package entity.agent;

import org.joml.Vector2f;

import entity.Entity;
import entity.Transform;
import render.Animation;

public abstract class Agent extends Entity {
	private AgentAnim currentAnim;
	private Vector2f currentDirection;
	
	private String name;
	private float speed;

	private boolean isMoving;
	
	protected MoveStrategy movement;

	public Agent(String name, Transform transform) {
		super(AgentAnim.AMOUNT, transform);
		
		this.name = name;
		
		this.currentAnim = AgentAnim.IDLE_E;
		this.speed = 5.0f;
		this.currentDirection = new Vector2f(.0f, .0f);
		this.isMoving = false;
		
		this.movement = new RandomMoveStrategy(this);
		
		for (AgentAnim anim : AgentAnim.values()) {
			setAnimation(anim.index(), 
					new Animation(anim.amount(), 8, name + "/" + anim.path()));
		}
	}
	
	public String getName() {return this.name;}
	public float getSpeed() {return this.speed;}
	public boolean isMoving() {return this.isMoving;}
	
	public void setSpeed(float speed) {this.speed = speed;}
	
	protected void move(float delta) {move(delta, this.currentDirection);}
	
	protected void move(float delta, Vector2f direction) {
		Vector2f movement = new Vector2f();
		movement.add(speed * delta * direction.x, 
				speed * delta * direction.y);
		
		this.isMoving = movement.length() != 0? true : false;
		
		this.currentDirection = direction;
		
		updateAnimation(direction);
		
		super.move(movement);
	}
	
	public void updateAnimation(Vector2f direction) {
		if (direction.length() > 0) {
			if (Math.abs(direction.x) > Math.abs(direction.y)) {
				if (direction.x > 0) selectAnimation(AgentAnim.WALK_E);
				else selectAnimation(AgentAnim.WALK_W);
			} else {
				if (direction.y > 0) selectAnimation(AgentAnim.WALK_N);
				else selectAnimation(AgentAnim.WALK_S);
			}
		} else {
			if (currentAnim.isWalking()) 
				selectAnimation(currentAnim.idle());
		}
	}
	
	public void selectAnimation(AgentAnim anim) {
		this.currentAnim = anim;
		super.useAnimation(anim.index());
	}
}
