package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;

import java.util.List;

public interface OpenverseProxy {

    ResourceManager getResourceManager();

    List<?> getPlayers();

    Universe getUniverse();

    Endpoint getEndpoint();
}
