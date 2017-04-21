package xyz.upperlevel.openverse;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.openverse.world.World;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class Openverse {

    public static OpenverseCommonProxy proxy;

    public static void setProxy(OpenverseCommonProxy proxy) {
        Openverse.proxy = proxy;
    }

    public static OpenverseCommonProxy getProxy() {
        return proxy;
    }


    public List<?> getPlayers() {
        return proxy.getPlayers();
    }

    public Map<String, World> getWorlds() {
        return proxy.getWorlds();
    }

    public Endpoint getEndpoint() {
        return proxy.getEndpoint();
    }
}
