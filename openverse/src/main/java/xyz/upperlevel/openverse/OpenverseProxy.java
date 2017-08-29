package xyz.upperlevel.openverse;

import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.resource.Resources;

public interface OpenverseProxy {
    EventManager getEventManager();

    Resources getResources();

    Endpoint getEndpoint();

    Channel getChannel();
}
