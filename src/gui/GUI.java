package gui;

import assets.Assets;
import game.Shader;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import render.Camera;

public class GUI {
    private Shader shader;

    public GUI() {
        this.shader = new Shader("gui");
    }

    public void render(Camera camera) {
        Matrix4f mat = new Matrix4f();

        camera.getUntransformedProjection().scale(87, mat);
        mat.translate(-3, -3, 0);

        shader.bind();
        shader.setUniform("projection", mat);
        shader.setUniform("color", new Vector4f(0, 0, 0, 0.4f));

        Assets.getModel().render();
    }
}
