package com.articulatagame;

public abstract class GameState {

    //TODO: pass in some data later on.
    public GameState() {
    }

    public abstract void render(int screenWidth, int screenHeight);

    public abstract void update();

    public abstract void onLoad();

    public abstract void onUnload();
}
