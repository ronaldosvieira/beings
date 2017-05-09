package gui;

import assets.Assets;
import collision.AABB;
import collision.QuadTree;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import render.Camera;
import world.World;

public class QuadTreeViz extends GUI {
    private World world;
    private static Vector3f[] colors = {
            new Vector3f(1, 0, 0),
            new Vector3f(1,.2f,0),
            new Vector3f(1,.4f,0),
            new Vector3f(1,.6f,0),
            new Vector3f(1,.8f,0),
            new Vector3f(1,1,0)
    };

    public QuadTreeViz(World world) {
        this.world = world;
    }

    @Override
    public void render(Camera camera) {
        renderQuadTree(this.world.getQuadTree(), camera);
    }

    public void renderQuadTree(QuadTree quad, Camera camera) {
        QuadTree[] nodes = quad.getNodes();

        System.out.println(quad.getBounds().getCenter());

        drawRect(quad.getBounds(), colors[quad.getLevel()], camera);

        if (nodes[0] != null) {
            renderQuadTree(nodes[0], camera);
            renderQuadTree(nodes[1], camera);
            renderQuadTree(nodes[2], camera);
            renderQuadTree(nodes[3], camera);
        }
    }

    private void drawRect(AABB rect, Vector3f color, Camera camera) {
        Vector2f center = rect.getCenter().mul(1, -1, new Vector2f()).mul(2);
        Vector2f he = rect.getHalfExtent().mul(2, new Vector2f());

        Vector2f left = center.add(-he.x, 0, new Vector2f());
        Vector2f right = center.add(he.x, 0, new Vector2f());
        Vector2f top = center.add(0, he.y, new Vector2f());
        Vector2f bottom = center.add(0, -he.y, new Vector2f());

        float resp = .1f * (float) camera.getZoom();

        Vector3f leftSize = new Vector3f(resp, he.y, 0);
        Vector3f rightSize = new Vector3f(resp, he.y, 0);
        Vector3f topSize = new Vector3f(he.x, resp, 0);
        Vector3f bottomSize = new Vector3f(he.x, resp, 0);

        //Vector3f color = new Vector3f(1f, 0f, 0f);

        drawLine(new Vector3f(left, 0f), leftSize, color, camera);
        drawLine(new Vector3f(right, 0f), rightSize, color, camera);
        drawLine(new Vector3f(top, 0f), topSize, color, camera);
        drawLine(new Vector3f(bottom, 0f), bottomSize, color, camera);
    }

    private void drawLine(Vector3f pos, Vector3f size, Vector3f color, Camera camera) {
        Matrix4f mat = camera.getProjection();
        mat.mul(world.getWorldMatrix());

        mat.translate(pos).scale(size);

        getShader().bind();
        getShader().setUniform("projection", mat);
        getShader().setUniform("color", new Vector4f(color, .5f));

        Assets.getModel().render();
    }
}
