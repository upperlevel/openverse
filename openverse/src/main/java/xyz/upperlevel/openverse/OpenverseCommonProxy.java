package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.openverse.world.World;

import java.util.List;
import java.util.Map;

public interface OpenverseCommonProxy {

    List<?> getPlayers();

    Map<String, World> getWorlds();

    Endpoint getEndpoint();
}
