package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import org.lwjgl.system.CallbackI;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.client.world.updater.PlayerLocationWatcher;
import xyz.upperlevel.openverse.console.log.OpenverseLogger;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.opengl.buffer.Vao;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Scene instantiated when the player, the world and the initial chunks are received.
 */
@Getter
public class PlayingWorldScene implements Scene {
    private final WorldViewer worldViewer;
    private final PlayerLocationWatcher playerWatcher;
    private long ticksEach;
    private long lastTick;

    public PlayingWorldScene(LivingEntity player) {
        this.worldViewer = new WorldViewer(player);
        this.playerWatcher = new PlayerLocationWatcher(player);
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
        float partialTicks = (System.currentTimeMillis() - lastTick) / (float)ticksEach;
        worldViewer.render(partialTicks);
    }
}
