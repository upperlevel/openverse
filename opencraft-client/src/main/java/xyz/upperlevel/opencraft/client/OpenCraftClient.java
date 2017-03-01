package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.network.player.ClientListenerSubscriber;
import xyz.upperlevel.opencraft.client.render.WorldViewer;

public class OpenCraftClient {

    public static final OpenCraftClient GET = new OpenCraftClient();

    @Getter
    private WorldViewer viewer = new WorldViewer();

    public OpenCraftClient() {
        start();
    }

    public void start() {
        ClientListenerSubscriber.subscribe();
    }

    public static OpenCraftClient get() {
        return GET;
    }
}
