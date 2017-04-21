package xyz.upperlevel.openverse.resource;

import lombok.Getter;

public class ResourceManager {

    @Getter
    private ModelManager modelManager = new ModelManager();

    @Getter
    private EntityTypeManager entityTypeManager = new EntityTypeManager();

    @Getter
    private BlockTypeManager blockTypeManager = new BlockTypeManager();

    public ResourceManager() {
    }
}
