package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.ulge.game.Scene;

@Getter
@RequiredArgsConstructor
public class ResourceScene implements Scene {
    private final ClientScene parent;

    @Override
    public void onEnable(Scene previous) {
        Openverse.logger().info("Joined resource scene.");
        Openverse.logger().info("Loading and setting up client resources...");

        Openverse.resources().setup();
        Openverse.resources().load();

        parent.setScene(new GameScene(parent));
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
        GL11.glClearColor(1f, 0, 0, 0);
    }
}
