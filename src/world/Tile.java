package world;

public class Tile {
	public static Tile tiles[] = new Tile[16];
	public static byte amount = 0;
	
	public static final Tile testTile = new Tile("tiles/grass/0");
	public static final Tile testTile2 = new Tile("tiles/sand/0");
	
	private byte id;
	private boolean solid;
	private String texture;
	
	public Tile(String texture) {
		this.id = amount++;
		this.texture = texture;
		this.solid = false;
		
		if (tiles[id] != null) throw new IllegalStateException("Tile at" + id + " is already being used.");
		
		tiles[id] = this;
	}

	public void setId(byte id) {
		this.id = id;
	}
	
	public boolean isSolid() {
		return this.solid;
	}
	
	public Tile setSolid() {
		this.solid = true;
		return this;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	public byte getId() {return id;}
	public String getTexture() {return texture;}
}
