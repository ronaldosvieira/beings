package world;

import collision.AABB;
import collision.QuadTree;
import entity.Entity;
import entity.model.Thing;
import entity.model.mind.sense.Perception;
import game.Shader;
import io.Window;
import knowledge.KnowledgeBase;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import render.Camera;
import render.TileRenderer;
import util.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class World {
	private int viewX, viewY;
	private byte[] tiles;
	private AABB[] boundingBoxes;
	private List<Entity> entities;
	private int width, height;
	private Matrix4f world;
	private int scale;

	private KnowledgeBase knowledgeBase;

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
                new AABB(new Vector2f(this.width / 2f, this.height / 2f),
                        new Vector2f(this.width / 2f, this.height / 2f)));

        this.knowledgeBase = new KnowledgeBase();

        try {
            this.knowledgeBase.load("semantic");
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.exit(1);
        }

		this.world = new Matrix4f().translate(0, 0, 0);
		this.world.scale(scale);
	}
	
	public World(Map map) {
		this.width = map.getWidth();
		this.height = map.getHeight();
		this.scale = map.getScale();
	
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

        this.knowledgeBase = new KnowledgeBase();

        try {
            this.knowledgeBase.load("semantic");
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.exit(1);
        }

		this.entities = new ArrayList<>();
		this.collisions = new HashSet<>();
        this.quad = new QuadTree(0,
                new AABB(new Vector2f(this.width / 2, this.height / 2),
                        new Vector2f(this.width / 2, this.height / 2)));
	}
	
	public void update(float delta, Window window, Camera camera) {
        quad.clear();
		collisions.clear();

        for (Entity entity : entities) {
			entity.update(delta, window, camera);

			Vector2f position = entity.getPosition();

			position.x = Math.max(0, position.x);
			position.x = Math.min(position.x, getWidth() - 1);

			position.y = Math.min(0, position.y);
			position.y = Math.max(position.y, -(getHeight() - 1));

			entity.setPosition(position);

			quad.insert(entity);
		}

        for (Entity entity : entities) {
            entity.collideWithTiles();

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

            entity.collideWithTiles();
        }

		for (Entity entity : entities) {
			entity.cycle();
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

        sortedEntities.sort((Entity o1, Entity o2) -> {
            if (o1.getPosition().y > o2.getPosition().y)
                return -1;
            else if (o1.getPosition().y < o2.getPosition().y)
                return 1;
            else
                return 0;
        });

		List<Entity> walkableEntities = new ArrayList<>(sortedEntities)
                .parallelStream()
                .filter(entity -> entity.isWalkable())
                .collect(Collectors.toList());
        List<Entity> nonWalkableEntities = new ArrayList<>(sortedEntities)
                .parallelStream()
                .filter(entity -> !entity.isWalkable())
                .collect(Collectors.toList());

		for (Entity entity: walkableEntities) {
			entity.render(shader, camera);
		}

		for (Entity entity: nonWalkableEntities) {
		    entity.render(shader, camera);
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

	public List<Perception> getNearEntities(Entity entity, float range) {
	    AABB box = new AABB(entity.getPosition(), new Vector2f(range));

	    return this.quad
                .retrieve(box)
                .stream()
                .filter(entity2 -> !entity.equals(entity2))
                .filter(entity2 -> entity.getPosition()
                        .distance(entity2.getPosition()) <= range)
                .map(entity1 -> (Thing) entity1)
                .map(thing -> {
                    Perception perception = new Perception(thing.getSemantic());
                    Vector2f pos1 = thing.getPosition();
                    Vector2f pos2 = entity.getPosition();

					perception.set("distance",
                            pos1.sub(pos2, new Vector2f()));

                    return perception;
                })
                .collect(Collectors.toList());
	}

	public void addEntity(Entity entity) {
	    if (this.entities.contains(entity)) return;

        float x = entity.getPosition().x;
        float y = -entity.getPosition().y;

        if (x < 0 || x >= this.width * this.scale || y < 0 || y >= this.height * this.scale) {
            throw new IllegalArgumentException("Entity placement out of bounds.");
        }

	    this.entities.add(entity);
    }

	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public KnowledgeBase getKnowledgeBase() {return this.knowledgeBase;}
}
