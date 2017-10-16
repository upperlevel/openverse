package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.ulge.game.Scene;

/**
 * Scene instantiated when the player, the world and the initial chunks are received.
 */
@Getter
@RequiredArgsConstructor
public class PlayingWorldScene implements Scene {
    private final WorldViewer worldViewer;

    @Override
    public void onEnable(Scene scene) {
        worldViewer.listen();
    }

    @Override
    public void onDisable(Scene scene) {
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onFps() {
    }

    @Override
    public void onRender() {
        worldViewer.render();
    }
}
