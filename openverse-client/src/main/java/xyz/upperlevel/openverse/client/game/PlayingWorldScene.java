package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import org.joml.Vector3i;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.client.world.updater.PlayerLocationWatcher;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.util.math.LineVisitor3d;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.opengl.buffer.Vao;
import xyz.upperlevel.ulge.util.Screenshot;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.MouseButtonChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;
import xyz.upperlevel.ulge.window.event.button.MouseButton;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/**
 * Scene instantiated when the player, the world and the initial chunks are received.
 */
@Getter
public class PlayingWorldScene implements Scene, Listener {
    private static final File SCREENSHOTS_DIR = new File("screenshots");
    private final WorldViewer worldViewer;
    private final PlayerLocationWatcher playerWatcher;
    private long ticksEach;
    private long lastTick;

    static {
        SCREENSHOTS_DIR.mkdir();
    }

    public PlayingWorldScene(Player player) {
        OpenverseClient.get().setPlayer(player);
        this.worldViewer = new WorldViewer(player);
        this.playerWatcher = new PlayerLocationWatcher(player);
        Window window = OpenverseLauncher.get().getGame().getWindow();
        window.getEventManager().register(this);
    }

    @Override
    public void onEnable(Scene scene) {
        worldViewer.listen();
        ticksEach = OpenverseLauncher.get().getGame().getTickEach();
        lastTick = System.currentTimeMillis();
    }

    @Override
    public void onDisable(Scene scene) {
    }

    @Override
    public void onTick() {
        lastTick = System.currentTimeMillis();
        playerWatcher.update();
    }

    public float getPartialTicks() {
        return (System.currentTimeMillis() - lastTick) / (float)ticksEach;
    }

    @Override
    public void onFps() {
        Openverse.getLogger().info("Fps: " + OpenverseLauncher.get().getGame().getFps() +
                " chunks: " + worldViewer.getWorldSession().getChunkView().getChunks().size() +
                ", vaos:" + Vao.instances +
                ", tick:" + EntityManager.ENTITY_TICK_PROFILER.getAverageNanos() + "(" + EntityManager.ENTITY_TICK_PROFILER.getCallCount() + ")"
        );
        EntityManager.ENTITY_TICK_PROFILER.reset();//TODO: add ProfileSystem
    }

    @Override
    public void onRender() {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glClearColor(147f / 255f, 224f / 255f, 255f / 255f, 0);

        worldViewer.render(getPartialTicks());
    }

    @EventHandler
    public void onKey(KeyChangeEvent event) {
        if (event.getAction() == Action.PRESS) {
            switch (event.getKey()) {
                case L:
                    Location loc = worldViewer.getEntity().getLocation(getPartialTicks());
                    Openverse.getLogger().info("loc: " + loc.toStringComplete());
                    break;
                case F1:
                    File file = new File(SCREENSHOTS_DIR, System.currentTimeMillis() + ".png");
                    try {
                        Screenshot.saveToFile(OpenverseLauncher.get().getGame().getWindow(), file, "png");
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                    Openverse.getLogger().fine("Screenshot saved in " + file.getAbsolutePath());
                    break;
            }
        }
    }

    @EventHandler
    public void onClick(MouseButtonChangeEvent e) {
        if (e.getAction() == Action.PRESS) {
            Entity clicker = worldViewer.getEntity();
            LineVisitor3d.RayCastResult rayCast = clicker.rayCast(getPartialTicks());
            if (rayCast == null) {
                Openverse.getLogger().info("Clicked nothing");
            } else {
                Vector3i loc = rayCast.getBlock();
                Openverse.getLogger().info("Clicked " + clicker.getWorld().getBlock(loc));
                if (e.getButton() == MouseButton.LEFT) {
                    // TODO: only player can break blocks, right? 0_o
                    ((Player) clicker).breakBlock(loc.x, loc.y, loc.z);
                } else {
                    ((Player) clicker).useItemInHand(loc.x, loc.y, loc.z, rayCast.getFace());
                }
            }
        }
    }
}
