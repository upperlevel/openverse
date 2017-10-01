package xyz.upperlevel.openverse;

import xyz.upperlevel.event.EventManager;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.resource.Resources;
import xyz.upperlevel.openverse.world.entity.EntityManager;

import java.util.logging.Logger;

public interface OpenverseProxy {
    EventManager getEventManager();

    Logger getLogger();

    Resources getResources();

    Endpoint getEndpoint();

    Channel getChannel();

    EntityManager getEntityManager();
}
