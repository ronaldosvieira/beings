package world;

import entity.Entity;

import java.util.List;

public class Map {
	private int[][] tiles;
	private int width, height;
	private int scale;
	
	public Map(int[][] tiles, int scale) {
		this.width = tiles.length;
		
		if (this.width < 1) 
			throw new IllegalArgumentException("Map width should be greater than 0.");
		
		this.height = tiles[0].length;
		
		if (this.height < 1) 
			throw new IllegalArgumentException("Map height should be greater than 0.");

		this.scale = scale;

        this.tiles = tiles;
	}
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public int getScale() {return this.scale;}
	public int[][] getTiles() {return this.tiles;}
	public int getTile(int x, int y) {return this.tiles[x][y];}
}
