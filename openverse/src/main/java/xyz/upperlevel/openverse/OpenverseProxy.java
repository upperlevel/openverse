package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;

public interface OpenverseProxy {

    ResourceManager getResourceManager();

    PlayerManager getPlayerManager();

    Universe getUniverse();

    Endpoint getEndpoint();

    Channel getChannel();
}
