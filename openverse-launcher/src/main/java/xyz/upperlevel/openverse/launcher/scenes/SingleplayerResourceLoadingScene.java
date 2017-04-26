package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.ulge.game.Scene;

import java.io.File;

import static java.lang.System.out;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * Stage opened while the client is connecting to a universe.
 * It loads resources.
 */
public class SingleplayerResourceLoadingScene implements Scene {

    @Getter
    private final SingleplayerUniverseScene parent;

    @Getter
    private final File root = new File("resources");

    public SingleplayerResourceLoadingScene(@NonNull SingleplayerUniverseScene parent) {
        this.parent = parent;
    }

    private void load() {
        // loads client and server resources based on a root dir
        parent.getClient().getResourceManager().load(root);
        parent.getServer().getResourceManager().load(root);
    }

    @Override
    public void onEnable(Scene prev) {
        // loads resources
        out.println("Loads client/server resources...");
        load();
        out.println("Resources loaded!");
    }

    @Override
    public void onRender() {
        glClearColor(1, 0, 0, 1);
    }
}
