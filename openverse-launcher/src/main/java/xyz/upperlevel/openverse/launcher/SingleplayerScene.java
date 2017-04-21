package xyz.upperlevel.openverse.launcher;

import lombok.Getter;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.ulge.game.Scene;

public class SingleplayerScene implements Scene {

    @Getter
    private

    @Getter
    private WorldViewer viewer = new WorldViewer();

    public SingleplayerScene() {
    }

    @Override
    public void onRender() {

    }
}
