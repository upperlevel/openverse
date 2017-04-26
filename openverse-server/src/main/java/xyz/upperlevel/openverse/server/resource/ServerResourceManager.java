package xyz.upperlevel.openverse.server.resource;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.ModelManager;
import xyz.upperlevel.openverse.resource.ResourceManager;

public class ServerResourceManager extends ResourceManager {

    @Getter
    private final ModelManager modelManager = new ModelManager();

    public ServerResourceManager() {
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }
}
