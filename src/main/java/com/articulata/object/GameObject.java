package com.articulata.object;

import com.articulata.object.rendering.Model;

/**
 * Created by Tim Verhaegen on 21/06/2017.
 */
public class GameObject {

    private final Model model;
    public int x;
    public int y;
    public int z;

    public int width;
    public int height;
    public int length;

    public int scale;

    public int color;

    public GameObject(int x, int y, int z, int width, int height, int length, int color, Model model) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.length = length;
        this.color = color;

        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
