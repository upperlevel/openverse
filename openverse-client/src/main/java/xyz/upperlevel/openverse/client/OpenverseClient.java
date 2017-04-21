package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.World;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class OpenverseClient implements OpenverseProxy {//TODO Implement

    @Getter
    private ResourceManager resourceManager = new ResourceManager();

    @Override
    public List<?> getPlayers() {
        return singletonList(getPlayer());
    }
}
