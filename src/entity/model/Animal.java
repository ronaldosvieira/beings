package entity.model;

import org.joml.Vector2f;

import entity.Transform;
import entity.model.strategies.FeedingStrategy;
import entity.model.strategies.MoveStrategy;
import entity.model.strategies.RandomMoveStrategy;
import entity.model.util.AnimalAnim;
import render.Animation;

public abstract class Animal extends LivingThing {
	protected FeedingStrategy feeding;
	protected MoveStrategy movement;
	
	private AnimalAnim currentAnim;
	private Vector2f currentDirection;
	
	private double hunger;
	private double thirst;
	
	private boolean isMoving;
	private float speed;

	public Animal(String name, Transform transform) {
		super(name, AnimalAnim.AMOUNT, transform);
		
		this.currentAnim = AnimalAnim.IDLE_E;
		this.currentDirection = new Vector2f(.0f, .0f);
		
		this.speed = 5.0f; // default speed
		this.isMoving = false;
		
		this.movement = new RandomMoveStrategy(this);
		
		for (AnimalAnim anim : AnimalAnim.values()) {
			setAnimation(anim.index(), 
					new Animation(anim.amount(), 8, name + "/" + anim.path()));
		}
	}
	
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
}
