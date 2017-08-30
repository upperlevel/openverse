package xyz.upperlevel.openverse.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
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
        OpenverseLauncher.get().getGame().getWindow().getEventManager().register(this);
        OpenverseLauncher.get().getGame().getWindow().disableCursor();
    }

    @Override
    public void onEnable(Scene previous) {
        System.out.println("[Client] Game scene set up! Start rendering world viewer.");
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
        Openverse.logger().info("Fps: " + OpenverseLauncher.get().getGame().getFps());
    }

    @Getter(AccessLevel.NONE)
    private double lastCursorX, lastCursorY;

    @EventHandler
    public void onCursorMove(CursorMoveEvent e) {
        // System.out.println("[Client] Detected a cursor movement: " + e.getX() + " " + e.getY());
        float dx = (float) (e.getX() - lastCursorX);
        float dy = (float) (e.getY() - lastCursorY);
        viewer.rotateLook(dx, dy);
        lastCursorX = e.getX();
        lastCursorY = e.getY();
        // System.out.println("[Client] Player rotate look of: " + dx + " " + dy);
    }

    private static final float SPEED = 1.5f;

    @EventHandler
    public void onKeyChange(KeyChangeEvent e) {
        // System.out.println("[Client] Detected a key input: " + e.getKey().name() + " " + e.getAction().name());
        if (e.getAction() == Action.PRESS) {
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
                case ESCAPE:
                    OpenverseLauncher.get().getGame().stop();
            }
        }
        // System.out.println("[Client] Move to: " + viewer.getX() + " " + viewer.getY() + " " + viewer.getZ());
    }
}
