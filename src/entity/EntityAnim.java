package entity;

public enum EntityAnim {
	IDLE_N (0, 1, "idle_N"),
	IDLE_S (1, 1, "idle_S"),
	IDLE_W (2, 1, "idle_W"),
    IDLE_E (3, 1, "idle_E"),
    WALK_N (4, 3, "walking_N"),
    WALK_S (5, 3, "walking_S"),
    WALK_W (6, 3, "walking_W"),
    WALK_E (7, 3, "walking_E");

    private final int index;
    private final int amount;
    private final String path;
    
    public static final int AMOUNT = 8;
    
    EntityAnim(int index, int amount, String path) {
        this.index = index;
        this.amount = amount;
        this.path = path;
    }
    
    public int index() {return this.index;}
    public int amount() {return this.amount;}
    public String path() {return this.path;}
    public boolean isIdle() {return this.index < 4;}
    public boolean isWalking() {return this.index >= 4;}
    
    public EntityAnim toggle() {
    	return EntityAnim.values()[(this.index + 4) % 4];
    }
    
    public EntityAnim walk() {
    	if (this.index < 4) {
    		return EntityAnim.values()[this.index + 4];
    	} else {
    		return this;
    	}
    }
    
    public EntityAnim idle() {
    	if (this.index < 4) {
    		return this;
    	} else {
    		return EntityAnim.values()[this.index - 4];
    	}
    }
}