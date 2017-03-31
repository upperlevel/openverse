package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.render.RenderContext;
import xyz.upperlevel.opencraft.client.resource.ResourceManager;
import xyz.upperlevel.ulge.window.Glfw;
import xyz.upperlevel.ulge.window.Window;

public class Openverse {

    public static final Openverse instance = new Openverse();

    @Getter
    private ResourceManager resources = new ResourceManager();

    @Getter
    private RenderContext bakery = new RenderContext();

    public Openverse() {
    }

    public void start() {
        Window window = Glfw.createWindow(1000, 1000, "Openverse", false);
        window.contextualize();
        window.show();
    }

    public void stop() {

    }

    public static Openverse g() {
        return instance;
    }
}
