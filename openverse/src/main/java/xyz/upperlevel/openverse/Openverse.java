package xyz.upperlevel.openverse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.World;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class Openverse {

    public static OpenverseProxy proxy;

    public static void setProxy(OpenverseProxy proxy) {
        Openverse.proxy = proxy;
    }

    public static OpenverseProxy getProxy() {
        return proxy;
    }


    public static ResourceManager getResourceManager() {
        return proxy.getResourceManager();
    }

    public static List<?> getPlayers() {
        return proxy.getPlayers();
    }

    public static Map<String, World> getWorlds() {
        return proxy.getWorlds();
    }

    public static Endpoint getEndpoint() {
        return proxy.getEndpoint();
    }
}
