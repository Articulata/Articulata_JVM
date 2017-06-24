package com.articulatagame.object;

import com.articulatagame.object.rendering.Model;
import com.articulatagame.util.Location;

public class GameObject {

    private final Model model;
    public int color;
    protected Location location;

    public GameObject(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public Location getLocation() {
        return location;
    }
}
