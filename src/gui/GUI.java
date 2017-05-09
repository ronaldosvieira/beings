package gui;

import game.Shader;
import render.Camera;

public abstract class GUI {
    private Shader shader;

    public GUI() {
        this.shader = new Shader("gui");
    }

    public abstract void render(Camera camera);

    protected Shader getShader() {return this.shader;}
}
