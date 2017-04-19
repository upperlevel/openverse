package xyz.upperlevel.openverse.client;

import xyz.upperlevel.openverse.client.render.world.BufferedChunk;
import xyz.upperlevel.openverse.client.render.world.WorldView;
import xyz.upperlevel.ulge.window.Glfw;
import xyz.upperlevel.ulge.window.Window;

public class Openverse {

    public static final Openverse get = new Openverse();

    private WorldView view;

    public Openverse() {
    }

    public void start() {
        Window window = Glfw.createWindow(1000, 1000, "Openverse", false);
        window.contextualize();
        window.show();

        view = new WorldView(1);

        BufferedChunk chunk = new BufferedChunk(view, 0, 0, 0);
        view.setChunk(0, 0, 0, chunk);

    }

    public void stop() {

    }

    public static Openverse get() {
        return get;
    }
}
