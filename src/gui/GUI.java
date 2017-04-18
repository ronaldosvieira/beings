package gui;

import assets.Assets;
import game.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import render.Camera;

public class GUI {
    private Shader shader;

    public GUI() {
        this.shader = new Shader("gui");
    }

    public void render(Camera camera) {
        Matrix4f mat = new Matrix4f();

        Vector3f cameraScale = new Vector3f((float) camera.getWidth(), (float) camera.getHeight(), 1);

        Vector3f pos = new Vector3f(-0.95f, -0.9f, 0.0f);
        Vector3f size = new Vector3f(5, 20, 0).div(cameraScale);

        mat.translate(pos).scale(size);

        shader.bind();
        shader.setUniform("projection", mat);
        shader.setUniform("color", new Vector4f(1.f, 0, 0, 1.f));

        Assets.getModel().render();

        shader.setUniform("projection", mat.translate(3.5f, 0, 0));

        Assets.getModel().render();
    }
}
