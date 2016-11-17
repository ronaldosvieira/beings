package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import game.Shader;
import io.Window;
import render.Camera;
import render.TileRenderer;

public class World {
	private final int view = 24;
	private byte[] tiles;
	private AABB[] boundingBoxes;
	private Map map;
	private int width, height;
	private Matrix4f world;
	private int scale;
	
	public World() {
		this.width = 64;
		this.height = 64;
		this.scale = 16;
		
		tiles = new byte[width * height];
		boundingBoxes = new AABB[width * height];
		
		world = new Matrix4f().translate(0, 0, 0);
		world.scale(scale);
	}
	
	public World(Map map) {
//		try {
			this.map = map;
			
//			BufferedImage tileSheet = ImageIO.read(new File("./levels/" + world + "_tiles.png"));
//			BufferedImage entitySheet = ImageIO.read(new File("./levels/" + world + "_entity.png"));
			
			this.width = map.getWidth();
			this.height = map.getHeight();
			this.scale = 16;
		
			this.world = new Matrix4f().translate(0, 0, 0);
			this.world.scale(scale);
			
			tiles = new byte[width * height];
			boundingBoxes = new AABB[width * height];
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Tile tile;
					try {
						tile = Tile.tiles[map.getTile(x, y)];
					} catch (ArrayIndexOutOfBoundsException e) {
						tile = null;
					}
					
					if (tile != null) {
						setTile(tile, x, y);
					}
				}
			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void render(TileRenderer renderer, Shader shader, Camera camera, Window window) {
		int posX = ((int) camera.getPosition().x + (window.getWidth() / 2)) / (scale * 2);
		int posY = ((int) camera.getPosition().y - (window.getHeight() / 2)) / (scale * 2);
		
		for (int i = 0; i < view; i++) {
			for (int j = 0; j < view; j++) {
				Tile tile = getTile(i - posX, j + posY);
				
				if (tile != null) {
					renderer.renderTile(tile, i - posX, -j - posY, shader, world, camera);
				}
			}
		}
	}
	
	public Matrix4f getWorldMatrix() {
		return world;
	}
	
	public void correctCamera(Camera camera, Window window) {
		Vector3f pos = camera.getPosition();
		
		int w = -width * scale * 2;
		int h = height * scale * 2;
		
		if (pos.x > -(window.getWidth() / 2) + scale) 
			pos.x = -(window.getWidth() / 2) + scale;
		
		if (pos.x < w + (window.getWidth() / 2) + scale) 
			pos.x = w + (window.getWidth() / 2) + scale;
		
		if (pos.y < (window.getHeight() / 2) - scale) 
			pos.y = (window.getHeight() / 2) - scale;
		
		if (pos.y > h - (window.getHeight() / 2) - scale) 
			pos.y = h - (window.getHeight() / 2) - scale;
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getId();
		
		if (tile.isSolid()) {
			boundingBoxes[x + y * width] = 
					new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1, 1));
		} else {
			boundingBoxes[x + y * width] = null;
		}
	}
	
	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public AABB getTileBoundingBox(int x, int y) {
		try {
			return boundingBoxes[x + y * width];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public int getScale() {
		return scale;
	}
}
