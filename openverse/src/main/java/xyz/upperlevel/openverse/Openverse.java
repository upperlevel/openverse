package xyz.upperlevel.openverse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.resource.Resources;

@RequiredArgsConstructor
public final class Openverse {

    @Getter
    @Setter
    private static OpenverseProxy proxy;

    public static EventManager getEventManager() {
        return proxy.getEventManager();
    }

    public static Resources resources() {
        return proxy.getResources();
    }

    public static Endpoint getEndpoint() {
        return proxy.getEndpoint();
    }

    public static Channel getChannel() {
        return proxy.getChannel(); //TODO: create common channel
    }
}
