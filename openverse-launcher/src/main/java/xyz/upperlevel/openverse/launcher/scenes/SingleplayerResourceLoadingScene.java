package xyz.upperlevel.openverse.launcher.scenes;

import org.lwjgl.opengl.GL11;
import xyz.upperlevel.openverse.launcher.OpenverseSystem;
import xyz.upperlevel.ulge.game.Scene;

import static org.lwjgl.opengl.GL11.glClearColor;

public class SingleplayerResourceLoadingScene implements Scene {

    public SingleplayerResourceLoadingScene() {
    }

    @Override
    public void onEnable(Scene prev) {
        // starts loading resources

        // when finishes
        OpenverseSystem.getDirector().stage(new SingleplayerWorldScene());
    }

    @Override
    public void onRender() {
        glClearColor(1, 0, 0, 1);
    }
}
