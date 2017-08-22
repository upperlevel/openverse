package xyz.upperlevel.openverse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public final class Openverse {

    @Getter
    @Setter
    private static OpenverseProxy proxy;

    public static EventManager getEventManager() {
        return proxy.getEventManager();
    }

    public static ResourceManager getResourceManager() {
        return proxy.getResourceManager();
    }

    public static Endpoint getEndpoint() {
        return proxy.getEndpoint();
    }

    public static Channel getChannel() {
        return proxy.getChannel(); //TODO: create common channel
    }

    public static Universe getUniverse() {
        return proxy.getUniverse();
    }
}
