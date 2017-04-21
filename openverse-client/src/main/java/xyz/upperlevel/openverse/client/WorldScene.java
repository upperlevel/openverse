package xyz.upperlevel.openverse.client;

import lombok.Getter;
import xyz.upperlevel.openverse.client.world.WorldView;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.window.event.action.Action;
import xyz.upperlevel.ulge.window.event.key.Key;

public class WorldScene implements Scene {

    @Getter
    private WorldViewer viewer = new WorldViewer();

    @Getter
    private WorldView view = new WorldView(1);

    public WorldScene() {
    }

    @Override
    public void onTick() {
        // update the world
    }

    @Override
    public void onRender() {
        // just render the world
        view.render();
    }

    private static final float SENSITIVITY = 0.1f;

    @Override
    public void onKeyChange(Key key, Action action) {
        if (action == Action.PRESS) {
            switch (key) {
                case W:
                    viewer.y += SENSITIVITY;
                    break;
            }
        }
    }
}
