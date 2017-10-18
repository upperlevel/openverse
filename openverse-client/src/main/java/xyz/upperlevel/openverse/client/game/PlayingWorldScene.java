package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.client.world.updater.PlayerLocationWatcher;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.ulge.game.Scene;

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

    public PlayingWorldScene(LivingEntity player) {
        this.worldViewer = new WorldViewer(player);
        this.playerWatcher = new PlayerLocationWatcher(player);
    }

    @Override
    public void onEnable(Scene scene) {
        worldViewer.listen();
    }

    @Override
    public void onDisable(Scene scene) {
    }

    @Override
    public void onTick() {
        playerWatcher.update();
    }

    @Override
    public void onFps() {
    }

    @Override
    public void onRender() {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        worldViewer.render();
    }
}
