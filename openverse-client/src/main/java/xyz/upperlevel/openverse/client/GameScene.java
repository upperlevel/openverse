package xyz.upperlevel.openverse.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;
import xyz.upperlevel.ulge.window.event.key.Key;

// todo put in openverse launcher
@Getter
public class GameScene implements Scene, Listener {
    private final ClientScene parent;

    private WorldViewer viewer;

    public GameScene(ClientScene parent) {
        this.parent = parent;
        this.viewer = new WorldViewer();
    }

    @Override
    public void onEnable(Scene previous) {
        Openverse.logger().info("Game scene set up!");
    }

    @Override
    public void onRender() {
        viewer.render();
    }

    @Override
    public void onDisable(Scene next) {
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onFps() {
    }

    @Getter(AccessLevel.NONE)
    private double lastCursorX, lastCursorY;

    // TODO better as event
    @EventHandler
    public void onCursorMove(CursorMoveEvent e) {
        float dx = (float) (e.getX() - lastCursorX);
        float dy = (float) (e.getY() - lastCursorY);
        viewer.rotateLook(dx, dy);
        lastCursorX = e.getX();
        lastCursorY = e.getY();
        //g.getWindow().setCursorPosition(0, 0);
    }

    private static final float SPEED = 1.5f;

    @EventHandler
    public void onKeyChange(KeyChangeEvent e) {
        switch (e.getKey()) {
            case A:
                viewer.right(-SPEED);
                break;
            case D:
                viewer.right(SPEED);
                break;
            case W:
                viewer.forward(SPEED);
                break;
            case S:
                viewer.forward(-SPEED);
                break;
            case SPACE:
                viewer.up(SPEED);
                break;
            case LEFT_SHIFT:
                viewer.up(-SPEED);
                break;
        }
    }
}
