package com.articulatagame.gamestate;

import java.util.ArrayList;

import com.articulatagame.GameState;
import com.articulatagame.object.GameObject;
import com.articulatagame.object.player.ClientPlayer;
import com.articulatagame.util.Location;

public class GameStateMain extends GameState {

    ArrayList<GameObject> objects;

    public GameStateMain() {
        objects = new ArrayList<>();
        ClientPlayer player = new ClientPlayer("KikkerFroggo");
        player.setLocation(new Location(200, 200, 2));
        objects.add(player);
    }

    @Override
    public void render(int screenWidth, int screenHeight) {
        for (GameObject obj : objects) {
            obj.getModel().render(obj.getLocation().x, obj.getLocation().y, obj.getLocation().z);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }
}
