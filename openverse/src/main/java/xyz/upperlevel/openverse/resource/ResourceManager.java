package xyz.upperlevel.openverse.resource;

import lombok.Getter;

public abstract class ResourceManager {

    @Getter
    protected final BlockTypeManager blockTypeManager = new BlockTypeManager();

    public ResourceManager() {
    }

    public abstract ModelManager getModelManager();

    public abstract void load();

    public abstract void unload();
}
