package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.event.RenderOptionsChangeEvent;

public class RenderOptions {
    private static RenderOptions instance = new RenderOptions();

    public static RenderOptions get() {
        return instance;
    }

    public static void set(RenderOptions options) {
        RenderOptionsChangeEvent event = new RenderOptionsChangeEvent(options);
        Openverse.getEventManager().call(event);
        if(!event.isCancelled())
            instance = options;
    }

    @Getter
    @Setter
    private int renderDistance = 5;
}
