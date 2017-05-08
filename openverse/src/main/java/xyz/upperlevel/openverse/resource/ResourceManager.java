package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.model.ModelManager;

public abstract class ResourceManager {

    @Getter
    private final BlockTypeManager blockTypeManager;

    @Getter
    private final EntityTypeManager entityTypeManager;

    public ResourceManager() {
        blockTypeManager = new BlockTypeManager();
        entityTypeManager = new EntityTypeManager();
    }

    public void load() {
        onLoad();
    }

    protected void onLoad() {
    }

    public void unload() {
        entityTypeManager.clear();
        blockTypeManager.clear();
        getModelManager().clear();
        onUnload();
    }

    protected void onUnload() {
    }

    public abstract ModelManager getModelManager();
}
