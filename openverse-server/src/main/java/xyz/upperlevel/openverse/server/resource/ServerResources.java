package xyz.upperlevel.openverse.server.resource;

import xyz.upperlevel.openverse.resource.Resources;

import java.io.File;
import java.util.logging.Logger;

public class ServerResources extends Resources {
    public ServerResources(File folder, Logger logger) {
        super(folder, logger);
    }

    @Override
    protected ServerBlockTypeRegistry createBlockTypeRegistry(File folder, Logger logger) {
        return new ServerBlockTypeRegistry(folder, logger);
    }

    @Override
    public ServerBlockTypeRegistry blockTypes() {
        return (ServerBlockTypeRegistry) super.blockTypes();
    }
}
