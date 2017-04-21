package xyz.upperlevel.openverse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;

import java.util.List;

@RequiredArgsConstructor
public final class Openverse {

    @Getter
    @Setter
    public static OpenverseProxy proxy;


    public static ResourceManager getResourceManager() {
        return proxy.getResourceManager();
    }

    public static List<?> getPlayers() {
        return proxy.getPlayers();
    }

    public static Universe getWorlds() {
        return proxy.getUniverse();
    }

    public static Endpoint getEndpoint() {
        return proxy.getEndpoint();
    }
}
