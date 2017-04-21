package xyz.upperlevel.openverse.client;

import lombok.NonNull;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

import static org.lwjgl.opengl.GL11.glClearColor;

public class ResourceLoading implements Scene {

    public ResourceLoading() {
    }

    @Override
    public void onEnable(Scene prev) {
        // load resources
        // when end set next scene
        Openverse.get().getDirector().stage(new WorldScene());
    }

    @Override
    public void onDisable(Scene next) {
    }

    @Override
    public void onRender() {
        glClearColor(1, 0, 0, 0);
        // draw a string that shows the loading process
    }
}
