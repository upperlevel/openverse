package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.ulge.game.Scene;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;
import static org.lwjgl.opengl.GL11.glClearColor;

// this scene is used to load client and server resources
@Getter
public class SingleplayerResourceScene implements Scene {
    private final OpenverseLauncher launcher;
    private final SingleplayerScene parent;

    public SingleplayerResourceScene(@NonNull SingleplayerScene parent) {
        this.launcher = parent.getLauncher();
        this.parent = parent;
    }

    @Override
    public void onEnable(Scene previous) {
        System.out.println("Loading client and server resources...");
        parent.getClient().loadResources();
        parent.getServer().loadResources();
        System.out.println("Resources loaded!");

        System.out.println("Setting up playing scene...");
        parent.setScene(new SingleplayerPlayingScene(parent));
    }

    @Override
    public void onRender() {
        glClearColor(1f, 0, 0, 0);
    }
}
