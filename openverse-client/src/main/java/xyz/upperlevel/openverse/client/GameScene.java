package xyz.upperlevel.openverse.client;

import lombok.AccessLevel;
import lombok.Getter;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;

import static org.lwjgl.opengl.GL11.*;

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
        Openverse.logger().info("Listening for world packets...");
        viewer.listen();
    }

    @Override
    public void onRender() {
        Openverse.logger().info("Cleared all what should be cleared.");
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glClearColor(1, 0, 0, 0);
        Openverse.logger().info("Attempting to render...");
        viewer.render();
        Openverse.logger().info("Rendered!");
    }

    @Override
    public void onDisable(Scene next) {
        Openverse.getEventManager().call(new ShutdownEvent());
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
        // Openverse.logger().info(" Detected a cursor movement: " + e.getX() + " " + e.getY());
        float dx = (float) (e.getX() - lastCursorX);
        float dy = (float) (e.getY() - lastCursorY);
        viewer.rotateLook(dx, dy);
        lastCursorX = e.getX();
        lastCursorY = e.getY();
        // Openverse.logger().info(" Player rotate look of: " + dx + " " + dy);
    }

    private static final float SPEED = 1.5f;

    @EventHandler
    public void onKeyChange(KeyChangeEvent e) {
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
    }
}
