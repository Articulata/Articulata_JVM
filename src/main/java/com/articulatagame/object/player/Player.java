package com.articulatagame.object.player;

import com.articulatagame.object.GameObject;
import com.articulatagame.object.rendering.ModelCube;
import com.articulatagame.util.Location;

public abstract class Player extends GameObject implements IPlayer {
    public final String name;

    public Player(String name) {
        super(new ModelCube());
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setLocation(Location location) {
        super.location = location;
        System.out.println("Setting location: " + location);
    }

}
