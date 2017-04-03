package collision;

import entity.Entity;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;

    private int level;
    private List<Entity> entities;
    private AABB bounds;
    private QuadTree[] nodes;

    public QuadTree(int level, AABB bounds) {
        this.level = level;
        this.entities = new ArrayList<>();
        this.bounds = bounds;
        this.nodes = new QuadTree[4];
    }

    public void clear() {
        entities.clear();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    public void split() {
        int hW = (int) (bounds.getHalfExtent().x / 2);
        int hH = (int) (bounds.getHalfExtent().y / 2);
        int x = (int) (bounds.getCenter().x / 2);
        int y = (int) (bounds.getCenter().y / 2);

        nodes[0] = new QuadTree(level + 1,
                new AABB(new Vector2f(x + (2 * hW), y),
                        new Vector2f(hW, hH)));
        nodes[1] = new QuadTree(level + 1,
                new AABB(new Vector2f(x, y),
                        new Vector2f(hW, hH)));
        nodes[2] = new QuadTree(level + 1,
                new AABB(new Vector2f(x, y + (2 * hH)),
                        new Vector2f(hW, hH)));
        nodes[3] = new QuadTree(level + 1,
                new AABB(new Vector2f(x + (2 * hW), y + (2 * hH)),
                        new Vector2f(hW, hH)));
    }

    private int getIndex(Entity entity) {
        int index = -1;
        AABB rect = entity.getBoundingBox();
        Vector2f topLeft = rect.getCenter().sub(rect.getHalfExtent());

        // Object can completely fit within the top quadrants
        boolean topQuadrant = topLeft.y < bounds.getCenter().y
                && topLeft.y + (2 * rect.getHalfExtent().y) < bounds.getCenter().y;
        // Object can completely fit within thebottom quadrants
        boolean bottomQuadrant = topLeft.y > bounds.getCenter().y;

        // Object can completely fit within the left quadrants
        if (topLeft.x < bounds.getCenter().x
                && topLeft.x + (2 * rect.getHalfExtent().x) < bounds.getCenter().x) {
            if (topQuadrant) index = 1;
            else if (bottomQuadrant) index = 2;
        }
        // Object can completely fit within the right quadrants
        else if (topLeft.x > bounds.getCenter().x) {
            if (topQuadrant) index = 0;
            else if (bottomQuadrant) index = 3;
        }

        return index;
    }

    public void insert(Entity entity) {
        if (nodes[0] != null) {
            int index = getIndex(entity);

            if (index != -1) {
                nodes[index].insert(entity);

                return;
            }
        }

        entities.add(entity);

        if (entities.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) split();

            int i = 0;
            while (i < entities.size()) {
                int index = getIndex(entities.get(i));

                if (index != -1) {
                    nodes[index].insert(entities.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    public List<Entity> retrieve(Entity entity) {
        List<Entity> nearEntities = new ArrayList<>();

        int index = getIndex(entity);

        if (index != -1 && nodes[0] != null) {
            nearEntities.addAll(nodes[index].retrieve(entity));
        }

        nearEntities.addAll(entities);

        return nearEntities;
    }
}
