package xyz.upperlevel.openverse.client;

import lombok.AccessLevel;
import lombok.Getter;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.world.ChunkRenderer;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.opengl.buffer.Vao;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;
import xyz.upperlevel.ulge.window.event.key.Key;

import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;

// todo put in openverse launcher
@Getter
public class GameScene implements Scene, Listener {
    private static final float SPEED = 0.5f;
    private static final float SENSIBILITY = 0.5f;
    private final ClientScene parent;

    private WorldViewer viewer;
    private Window window;

    @Getter(AccessLevel.NONE)
    private double lastCursorX, lastCursorY;

    public GameScene(ClientScene parent) {
        this.parent = parent;
        this.viewer = new WorldViewer();
        window = OpenverseLauncher.get().getGame().getWindow();
        window.getEventManager().register(this);
        window.disableCursor();
    }

    @Override
    public void onEnable(Scene previous) {
        Openverse.logger().info("Listening for world packets...");
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);

        viewer.listen();
    }

    @Override
    public void onRender() {
        processInput();
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glClearColor(0.392f, 0.922f, 1f, 0);
        viewer.render();
    }

    private void processInput() {
        if (window.getKey(Key.A)) {
            viewer.right(-SPEED);
        } else if (window.getKey(Key.D)) {
            viewer.right(SPEED);
        }
        if (window.getKey(Key.W)) {
            viewer.forward(SPEED);
        } else if (window.getKey(Key.S)) {
            viewer.forward(-SPEED);
        }
        if (window.getKey(Key.SPACE)) {
            viewer.up(SPEED);
        } else if (window.getKey(Key.LEFT_SHIFT)) {
            viewer.up(-SPEED);
        }
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
        Openverse.logger().info("Fps: " + OpenverseLauncher.get().getGame().getFps() + " chunks: " + viewer.getWorldSession().getChunkView().getChunks().size() + ", vaos:" + Vao.instances);
    }

    @EventHandler
    public void onCursorMove(CursorMoveEvent e) {
        // Openverse.logger().info(" Detected a cursor movement: " + e.getX() + " " + e.getY());
        float dx = (float) (e.getX() - lastCursorX) * SENSIBILITY;
        float dy = (float) (e.getY() - lastCursorY) * SENSIBILITY;
        viewer.rotateLook(dx, dy);
        lastCursorX = e.getX();
        lastCursorY = e.getY();
        // Openverse.logger().info(" Player rotate look of: " + dx + " " + dy);
    }

    @EventHandler
    public void onKeyChange(KeyChangeEvent e) {
        if (e.getAction() == Action.PRESS) {
            switch (e.getKey()) {
                case ESCAPE:
                    OpenverseLauncher.get().getGame().stop();
                    break;
            }
        }
    }
}
