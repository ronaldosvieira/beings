package render;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import game.Shader;
import game.Texture;
import world.Tile;

public class TileRenderer {
	private Map<String, Texture> tileTextures;
	private Model model;
	
	public TileRenderer() {
		tileTextures = new HashMap<>();
		
		float[] vertices = new float[] {
				-1f, 1f, 0, // 0
				1f, 1f, 0,  // 1
				1f, -1f, 0, // 2
				-1f, -1f, 0,// 3
			};
			
		float[] texture = new float[] {0,0, 1,0, 1,1, 0,1};
		int[] indexes = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		model = new Model(vertices, texture, indexes);
		
		for (int i = 0; i < Tile.tiles.length; i++) {
			if (Tile.tiles[i] != null) {
				if (!tileTextures.containsKey(Tile.tiles[i].getTexture())) {
					String tex = Tile.tiles[i].getTexture();
					tileTextures.put(tex, new Texture(tex + ".png"));
				}
			}
		}
	}
	
	public void renderTile(Tile tile, int x, int y, 
			Shader shader, Matrix4f world, Camera camera) {
		shader.bind();
		
		if (tileTextures.containsKey(tile.getTexture())) {
			tileTextures.get(tile.getTexture()).bind(0);
		}
		
		Matrix4f tilePos = new Matrix4f().translate(x * 2, y * 2, 0);
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(world, target);
		target.mul(tilePos);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		model.render();
	}
}
