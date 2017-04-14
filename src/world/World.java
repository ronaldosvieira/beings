package world;

import collision.AABB;
import collision.QuadTree;
import entity.Entity;
import game.Shader;
import io.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import render.Camera;
import render.TileRenderer;
import util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;

public class World {
	private int viewX, viewY;
	private byte[] tiles;
	private AABB[] boundingBoxes;
	private List<Entity> entities;
	private int width, height;
	private Matrix4f world;
	private int scale;

	private QuadTree quad;
	private HashSet<Pair<Integer, Integer>> collisions;
	
	public World() {
		this.width = 64;
		this.height = 64;
		this.scale = 16;
		
		this.tiles = new byte[width * height];
		this.boundingBoxes = new AABB[width * height];
		
		this.entities = new ArrayList<>();
		this.collisions = new HashSet<>();
		this.quad = new QuadTree(0,
                new AABB(new Vector2f(this.width / 2, this.height / 2),
                        new Vector2f(this.width / 2, this.height / 2)));
		
		this.world = new Matrix4f().translate(0, 0, 0);
		this.world.scale(scale);
	}
	
	public World(Map map) {
		this.width = map.getWidth();
		this.height = map.getHeight();
		this.scale = 16;
	
		this.world = new Matrix4f().translate(0, 0, 0);
		this.world.scale(scale);
		
		this.tiles = new byte[width * height];
		this.boundingBoxes = new AABB[width * height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile tile = Tile.tiles[map.getTile(x, y)];
                setTile(tile, x, y);
			}
		}

		this.entities = map.getEntities();
		this.collisions = new HashSet<>();
        this.quad = new QuadTree(0,
                new AABB(new Vector2f(this.width / 2, this.height / 2),
                        new Vector2f(this.width / 2, this.height / 2)));
	}
	
	public void update(float delta, Window window, Camera camera) {
		float movement = 5 * 60 * delta;
		
		if (window.getInput().isKeyDown(GLFW_KEY_A)) {
			camera.addPosition(new Vector3f(movement, 0, 0));
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_D)) {
			camera.addPosition(new Vector3f(-movement, 0, 0));
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_W)) {
			camera.addPosition(new Vector3f(0, -movement, 0));
		}
		
		if (window.getInput().isKeyDown(GLFW_KEY_S)) {
			camera.addPosition(new Vector3f(0, movement, 0));
		}

        if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) ||
                window.getInput().getJoystickAxes(GLFW_JOYSTICK_6) > 0) {
		    if (camera.getZoom() > .25) {
		        camera.zoomIn();
		        calculateView(camera);
            }
		}

        if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT) ||
                window.getInput().getJoystickAxes(GLFW_JOYSTICK_5) > 0) {
		    if (camera.getZoom() < 3) {
		        camera.zoomOut();
		        calculateView(camera);
            }
        }

        if (window.getInput().getJoystickAxes(GLFW_JOYSTICK_1) != 0) {
            camera.addPosition(new Vector3f(
                    -movement * window.getInput().getJoystickAxes(GLFW_JOYSTICK_1), 0, 0));
        }

        if (window.getInput().getJoystickAxes(GLFW_JOYSTICK_2) != 0) {
            camera.addPosition(new Vector3f(
                    0, -movement * window.getInput().getJoystickAxes(GLFW_JOYSTICK_2), 0));
        }

        quad.clear();
		collisions.clear();

        for (Entity entity : entities) {
			entity.update(delta, window, camera, this);

			quad.insert(entity);
		}

		for (Entity entity : entities) {
            entity.cycle();
        }

        for (Entity entity : entities) {
            entity.collideWithTiles(this);

            for (Entity nearEntity : quad.retrieve(entity)) {
                if (entity.equals(nearEntity)) continue;

                Pair<Integer, Integer> pair =
                        new Pair<>(entity.getId(), nearEntity.getId());
                boolean checked = collisions.contains(pair)
                        || collisions.contains(pair.reverse());

                if (!checked) {
                    entity.collideWithEntities(nearEntity);
                    collisions.add(pair);
                }
            }

            entity.collideWithTiles(this);
        }
	}
	
	public void render(TileRenderer renderer, Shader shader, Camera camera) {
		int posX = (int) camera.getPosition().x / (scale * 2);
		int posY = (int) camera.getPosition().y / (scale * 2);

        for (int i = 0; i < viewX; i++) {
			for (int j = 0; j < viewY; j++) {
			    int tileX = i - posX - (viewX / 2) + 1;
			    int tileY = j + posY - (viewY / 2);

			    tileX = Math.max(0, tileX);
			    tileX = Math.min(tileX, width - 1);

			    tileY = Math.max(0, tileY);
			    tileY = Math.min(tileY, height - 1);

			    Tile tile = getTile(tileX, tileY);
				
				if (tile != null) {
					renderer.renderTile(
							tile, 
							i - posX - (viewX / 2) + 1, 
							-j - posY + (viewY / 2), 
							shader, world, camera);
				}
			}
		}

        List<Entity> sortedEntities = new ArrayList<>(entities);

        sortedEntities.sort((Entity o1, Entity o2) ->
                (int) (o2.getPosition().y - o1.getPosition().y));

		List<Entity> walkableEntities = new ArrayList<>(sortedEntities)
                .parallelStream()
                .filter(entity -> entity.isWalkable())
                .collect(Collectors.toList());
        List<Entity> nonWalkableEntities = new ArrayList<>(sortedEntities)
                .parallelStream()
                .filter(entity -> !entity.isWalkable())
                .collect(Collectors.toList());

		for (Entity entity: walkableEntities) {
			entity.render(shader, camera, this);
		}

		for (Entity entity: nonWalkableEntities) {
		    entity.render(shader, camera, this);
        }
	}
	
	public void calculateView(Camera camera) {
		viewX = (int) ((camera.getWidth() * camera.getZoom()) / (scale * 2)) + 4;
		viewY = (int) ((camera.getHeight() * camera.getZoom()) / (scale * 2)) + 4;
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

	public List<Entity> getEntities() {return this.entities;}

	public List<Entity> getNearEntities(Entity entity) {return this.quad.retrieve(entity);}

	public List<Entity> getNearEntities(AABB box) {return this.quad.retrieve(box);}

	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
}
