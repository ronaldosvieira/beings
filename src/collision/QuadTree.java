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

    private int getIndex(AABB box) {
        int index = -1;
        Vector2f topLeft = box.getCenter().sub(box.getHalfExtent(), new Vector2f());

        // Object can completely fit within the top quadrants
        boolean topQuadrant = topLeft.y < bounds.getCenter().y
                && topLeft.y + (2 * box.getHalfExtent().y) < bounds.getCenter().y;
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = topLeft.y > bounds.getCenter().y;

        // Object can completely fit within the left quadrants
        if (topLeft.x < bounds.getCenter().x
                && topLeft.x + (2 * box.getHalfExtent().x) < bounds.getCenter().x) {
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
            int index = getIndex(entity.getBoundingBox());

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
                int index = getIndex(entities.get(i).getBoundingBox());

                if (index != -1) {
                    nodes[index].insert(entities.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    public List<Entity> retrieve(Entity entity) {
        return retrieve(entity.getBoundingBox());
    }

    public List<Entity> retrieve(AABB box) {
        List<Entity> nearEntities = new ArrayList<>();

        int index = getIndex(box);

        if (index != -1 && nodes[0] != null) {
            nearEntities.addAll(nodes[index].retrieve(box));
        }

        nearEntities.addAll(entities);

        return nearEntities;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append("level: ").append(level).append("\n");
        out.append("entities: ").append(entities).append("\n");
        out.append("nodes: ").append("\n");

        if (nodes[0] != null) {
            for (QuadTree node : nodes) {
                out.append(node);
            }
        } else {
            out.append("none");
        }

        out.append("\n");

        return out.toString();
    }
}
