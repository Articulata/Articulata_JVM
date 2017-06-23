package com.articulata;

public abstract class GameState {

    public GameState() {

    }

    public abstract void render(int screenWidth, int screenHeight);

    public abstract void update();
}
