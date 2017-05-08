package xyz.upperlevel.openverse;

import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;

public interface OpenverseProxy {

    EventManager getEventManager();

    ResourceManager getResourceManager();

    Universe getUniverse();

    Endpoint getEndpoint();

    Channel getChannel();
}
