package assets;

import render.Model;

public class Assets {
    protected static Model model;

    public static Model getModel() {return model;}

    public static void initAsset() {
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
    }

    public static void deleteAsset() {
        model = null;
    }
}
