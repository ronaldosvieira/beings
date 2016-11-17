package world;

public class Map {
	private int[][] tiles;
	private int width, height;
	
	public Map(int[][] tiles) {
		this.width = tiles.length;
		
		if (this.width < 1) 
			throw new IllegalArgumentException("Map width should be at least 1.");
		
		this.height = tiles[0].length;
		
		if (this.height < 1) 
			throw new IllegalArgumentException("Map height should be at least 1.");
		 
		this.tiles = tiles;
	}
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public int[][] getTiles() {return this.tiles;}
	public int getTile(int x, int y) {return this.tiles[x][y];}
}
