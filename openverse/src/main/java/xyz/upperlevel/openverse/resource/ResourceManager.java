package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.model.ModelManager;

public abstract class ResourceManager {

    @Getter
    private final ModelManager modelManager = new ModelManager();

    @Getter
    private final BlockTypeManager blockTypeManager = new BlockTypeManager();

    @Getter
    private final EntityTypeManager entityTypeManager = new EntityTypeManager();

    public ResourceManager() {
    }

    public void load() {
        onLoad();
    }

    protected void onLoad() {
    }

    public void unload() {
        entityTypeManager.clear();
        blockTypeManager.clear();
        modelManager.clear();
        onUnload();
    }

    protected void onUnload() {
    }
}
