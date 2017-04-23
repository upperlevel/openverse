package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.ulge.game.Scene;

public class SingleplayerWorldScene implements Scene {

    @Getter
    private ClientWorld view = new ClientWorld(1);

    @Getter
    private WorldViewer viewer = new WorldViewer();

    public SingleplayerWorldScene() {
    }

    @Override
    public void onEnable(Scene prev) {
    }

    @Override
    public void onDisable(Scene next) {
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onRender() {
        view.render();
    }
}
