package xyz.upperlevel.openverse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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


    public static ResourceManager getResourceManager() {
        return proxy.getResourceManager();
    }

    public static List<Player> getPlayers() {
        return proxy.getPlayerManager();
    }

    public static Universe getWorlds() {
        return proxy.getUniverse();
    }

    public static Endpoint getEndpoint() {
        return proxy.getEndpoint();
    }

    public static Channel getChannel() {
        return proxy.getChannel();//TODO: create common channel
    }
}
