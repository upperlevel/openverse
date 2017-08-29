package xyz.upperlevel.openverse.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;
import xyz.upperlevel.ulge.window.event.key.Key;

// todo put in openverse launcher
@Getter
@RequiredArgsConstructor
public class GameScene implements Scene, Listener {
    private final OpenverseClient client;
    private WorldViewer viewer;

    @Override
    public void onEnable(Scene previous) {
        viewer = new WorldViewer();
    }

    @Override
    public void onRender() {
        viewer.render();
    }

    @Override
    public void onDisable(Scene next) {
    }

    @Getter(AccessLevel.NONE)
    private double lastCursorX, lastCursorY;

    // TODO better as event
    @Override
    public void onCursorMove(double x, double y) {
        float dx = (float) (x - lastCursorX);
        float dy = (float) (y - lastCursorY);
        viewer.rotateLook(dx, dy);
        lastCursorX = x;
        lastCursorY = y;
        //g.getWindow().setCursorPosition(0, 0);
    }

    @Override
    public void onKeyChange(Key key, Action action) {
        // todo key input
    }
}
