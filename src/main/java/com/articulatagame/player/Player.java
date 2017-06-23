package com.articulatagame.player;

import com.articulatagame.util.Location;

public abstract class Player implements IPlayer {
    public final String name;

    protected Location location;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setLocation(Location location) {
        this.location = location;
        System.out.println("Setting location: " + location);


    }

}
