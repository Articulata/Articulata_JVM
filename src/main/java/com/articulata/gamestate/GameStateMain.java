package com.articulata.gamestate;

import com.articulata.GameState;
import com.articulata.object.GameObject;
import com.articulata.object.rendering.ModelCube;

import java.util.ArrayList;

public class GameStateMain extends GameState {

    ArrayList<GameObject> objects;

    public GameStateMain() {
        objects = new ArrayList<>();
        objects.add(new GameObject(10, 10, 10, 100, 100, 100, 0xFF00FF, new ModelCube()));
    }

    @Override public void render(int screenWidth, int screenHeight) {
        for (GameObject obj : objects) {
            obj.getModel().render(screenWidth / 2 - obj.width / 2, screenHeight / 2 - obj.height / 2, 0, obj.width, obj.height, obj.length);
        }
    }

    @Override public void update() {

    }
}
