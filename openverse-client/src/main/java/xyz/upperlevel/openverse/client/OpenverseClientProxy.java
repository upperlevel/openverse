package xyz.upperlevel.openverse.client;

import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.openverse.OpenverseCommonProxy;
import xyz.upperlevel.openverse.world.World;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class OpenverseClientProxy implements OpenverseCommonProxy {//TODO Implement

    @Override
    public List<?> getPlayers() {
        return singletonList(getPlayers());
    }

    public Object getPlayer() {
        return null;
    }

    @Override
    public Map<String, World> getWorlds() {
        return null;
    }

    @Override
    public Endpoint getEndpoint() {
        return null;
    }
}
