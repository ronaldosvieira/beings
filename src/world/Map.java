package world;

import entity.Entity;

import java.util.List;

public class Map {
	private int[][] tiles;
	private List<Entity> entities;
	private int width, height;
	private int scale;
	
	public Map(int[][] tiles, List<Entity> entities, int scale) {
		this.width = tiles.length;
		
		if (this.width < 1) 
			throw new IllegalArgumentException("Map width should be greater than 0.");
		
		this.height = tiles[0].length;
		
		if (this.height < 1) 
			throw new IllegalArgumentException("Map height should be greater than 0.");

		this.scale = scale;

        for (Entity entity : entities) {
            float x = entity.getPosition().x;
            float y = -entity.getPosition().y;

            if (x < 0 || x >= this.width * this.scale || y < 0 || y >= this.height * this.scale) {
                throw new IllegalArgumentException("Entity placement out of bounds.");
            }
        }

        this.tiles = tiles;
		this.entities = entities;
	}
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public int getScale() {return this.scale;}
	public int[][] getTiles() {return this.tiles;}
	public List<Entity> getEntities() {return this.entities;}
	public int getTile(int x, int y) {return this.tiles[x][y];}
}
