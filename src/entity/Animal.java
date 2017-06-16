package entity;

import mind.Mind;
import mind.goal.Freeze;
import mind.goal.Goal;
import mind.goal.Explore;
import mind.need.Need;
import mind.sense.Sense;
import entity.util.AnimalAnim;
import io.Window;
import org.joml.Vector2f;
import render.Animation;
import render.Camera;
import world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends LivingThing {
    private Goal currentGoal;
	
	private AnimalAnim currentAnim;
	private Vector2f currentDirection;

	private Mind mind;
	private List<Sense> senses = new ArrayList<>();
	private List<Need> needs = new ArrayList<>();

	private boolean isMoving;
	private float movementSpeed;

	private int health = 100;

	public Animal(String name, World world, Vector2f scale, Vector2f position) {
		super(name, world, AnimalAnim.AMOUNT, scale, position);

		this.currentAnim = AnimalAnim.IDLE_N;
		this.currentDirection = new Vector2f(.0f, .1f);
		updateAnimation(this.currentDirection);

        this.mind = new Mind(this);
		
		this.movementSpeed = 5.0f; // default movement speed
		this.isMoving = false;
		
		this.currentGoal = new Explore(this);
		
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
			else {
                if (Math.abs(direction.x) > Math.abs(direction.y)) {
                    if (direction.x > 0) selectAnimation(AnimalAnim.IDLE_E);
                    else selectAnimation(AnimalAnim.IDLE_W);
                } else {
                    if (direction.y > 0) selectAnimation(AnimalAnim.IDLE_N);
                    else selectAnimation(AnimalAnim.IDLE_S);
                }
            }
		}
	}
	
	public void selectAnimation(AnimalAnim anim) {
		this.currentAnim = anim;
		super.useAnimation(anim.index());
	}

    @Override
    public void cycle(float delta) {
		if (health > 0) {
            this.needs.forEach(need -> need.decay(delta));

            this.mind.update();
        }
    }

    private void kill() {
        System.out.println(getName() + " killed");
        this.setCurrentGoal(new Freeze(this));
	    this.semantic.set("is-alive", false);
	    this.semantic.set("when-dead", System.currentTimeMillis());
    }

    @Override
    public void attack() {
	    this.health--;

		System.out.println(getName() + " health: " + health);

	    if (health <= 0) this.kill();
    }

	@Override
	public void update(float delta, Window window, Camera camera) {
		move(delta, this.currentGoal.getDirection());
	}

	public List<Sense> getSenses() {return this.senses;}

	public List<Need> getNeeds() {return this.needs;}

	protected void addSense(Sense sense) {
	    if (!this.senses.contains(sense)) this.senses.add(sense);
    }

	protected void addNeed(Need need) {
	    if (!this.needs.contains(need)) this.needs.add(need);
    }

    public Goal getCurrentGoal() {return this.currentGoal;}

    public void setCurrentGoal(Goal goal) {
        System.out.println(getName() + " new goal: " + goal);
        this.currentGoal = goal != null? goal : new Explore(this);
	}
}
