package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.List;

public interface OpenverseProxy {

    ResourceManager getResourceManager();

    List<Player> getPlayers();

    Universe getUniverse();

    Endpoint getEndpoint();

    Channel getChannel();
}
