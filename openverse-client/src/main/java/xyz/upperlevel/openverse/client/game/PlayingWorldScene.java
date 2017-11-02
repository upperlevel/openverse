package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.client.world.updater.PlayerLocationWatcher;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.util.math.LineVisitor3d;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.opengl.buffer.Vao;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.MouseButtonChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;
import xyz.upperlevel.ulge.window.event.button.MouseButton;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Scene instantiated when the player, the world and the initial chunks are received.
 */
@Getter
public class PlayingWorldScene implements Scene, Listener {
    private final WorldViewer worldViewer;
    private final PlayerLocationWatcher playerWatcher;
    private long ticksEach;
    private long lastTick;

    public PlayingWorldScene(LivingEntity player) {
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
        Openverse.logger().info("Fps: " + OpenverseLauncher.get().getGame().getFps() +
                " chunks: " + worldViewer.getWorldSession().getChunkView().getChunks().size() +
                ", vaos:" + Vao.instances +
                ", tick:" + EntityManager.ENTITY_TICK_PROFILER.getAverageNanos() + "(" + EntityManager.ENTITY_TICK_PROFILER.getCallCount() + ")"
        );
        EntityManager.ENTITY_TICK_PROFILER.reset();//TODO: add ProfileSystem
    }

    @Override
    public void onRender() {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        worldViewer.render(getPartialTicks());
    }

    @EventHandler
    public void onKey(KeyChangeEvent event) {
        if(event.getAction() == Action.PRESS) {
            switch (event.getKey()) {
                case L:
                    Location loc = worldViewer.getEntity().getLocation(getPartialTicks());
                    Openverse.logger().info("loc: " + loc.toStringComplete());
                    break;
            }
        }
    }

    @EventHandler
    public void onClick(MouseButtonChangeEvent e) {
        if (e.getAction() == Action.PRESS && e.getButton() == MouseButton.RIGHT) {
            Entity clicker = worldViewer.getEntity();
            LineVisitor3d.RayCastResult rayCast = clicker.rayCast(getPartialTicks());
            if (rayCast == null) {
                Openverse.logger().info("Clicked nothing");
            } else {
                Openverse.logger().info("Clicked " + clicker.getWorld().getBlock(rayCast.getBlock()));
            }
        }
    }
}
