package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.block.BlockTypeModelMapper;
import xyz.upperlevel.openverse.client.render.block.BlockModelRegistry;
import xyz.upperlevel.openverse.client.render.block.TextureBakery;
import xyz.upperlevel.ulge.game.Scene;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class ResourceScene implements Scene {
    private final ClientScene parent;

    @Override
    public void onEnable(Scene previous) {
        long init = System.currentTimeMillis();

        Openverse.logger().info("Loading resources...");

        BlockModelRegistry.load(new File("client/blocks/models"));
        BlockTypeModelMapper.map();
        TextureBakery.bake();

        Openverse.logger().info("Resources loaded in " + (System.currentTimeMillis() - init) + " ms.");

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
