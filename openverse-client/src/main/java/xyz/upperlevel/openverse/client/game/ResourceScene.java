package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.block.*;
import xyz.upperlevel.ulge.game.Scene;

import java.io.File;
import java.nio.file.Paths;

public class ResourceScene implements Scene {
    @Getter
    private final OpenverseClient client;

    @Getter
    private final ClientScene parent;

    public ResourceScene(OpenverseClient client, ClientScene parent) {
        this.client = client;

        this.parent = parent;
    }

    @Override
    public void onEnable(Scene previous) {
        long init = System.currentTimeMillis();

        client.getLogger().info("Loading resources...");

        client.getResources().setup();
        client.getResources().load();

        BlockModelRegistry.loadFolder(new File("resources/blocks/models"));
        BlockTypeModelMapper.load(client.getResources().blockTypes().entry("grass"), Paths.get("resources/blocks/grass.json"));
        BlockTypeModelMapper.load(client.getResources().blockTypes().entry("dirt"), Paths.get("resources/blocks/dirt.json"));
        BlockTypeModelMapper.load(client.getResources().blockTypes().entry("test"), Paths.get("resources/blocks/test.json"));
        BlockTypeModelMapper.load(client.getResources().blockTypes().entry("photon"), Paths.get("resources/blocks/photon.json"));

        TextureBakery.bake();
        BlockTypeModelMapper.bake(); // bakes models
        OpenverseClient.get().getItemRendererRegistry().registerDefaults(client.getResources().itemTypes()); // bakes item icons

        client.getLogger().info("Resources loaded in " + (System.currentTimeMillis() - init) + " ms.");

        parent.setScene(new GameScene(client, parent));
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
