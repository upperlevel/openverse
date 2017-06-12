package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.ulge.game.Scene;

import java.io.File;

import static java.lang.System.out;
import static org.lwjgl.opengl.GL11.glClearColor;

// this scene is used to load client and server resources
public class SingleplayerResourceLoadingScene implements Scene {

    @Getter
    private final SingleplayerUniverseScene parent;

    public SingleplayerResourceLoadingScene(@NonNull SingleplayerUniverseScene parent) {
        this.parent = parent;
    }

    @Override
    public void onEnable(Scene previous) {
        out.println("Attempting to load client resources...");
        parent.getClient().getResourceManager().load();
        out.println("Client resources has been loaded.");

        out.println("Attempting to load server resources...");
        parent.getServer().getResourceManager().load();
        out.println("Server resources has been loaded.");

        parent.setScene(new SingleplayerGameScene(parent));
    }

    @Override
    public void onRender() {
        glClearColor(1, 0, 0, 1);
    }
}
