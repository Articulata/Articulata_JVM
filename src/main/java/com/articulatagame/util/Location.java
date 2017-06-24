package com.articulatagame.util;

public class Location {
    public int x, y, z;

    public Location(Location location) {
        this(location.x, location.y, location.z);
    }

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location copy() {
        return new Location(this);
    }

    @Override
    public String toString() {
        return String.format("Location (%d, %d, %d)", x, y, z);
    }
}
