package xyz.upperlevel.openverse.client.resource;

import lombok.Getter;
import lombok.experimental.Accessors;
import xyz.upperlevel.openverse.resource.ModelManager;
import xyz.upperlevel.openverse.client.resource.program.Programs;
import xyz.upperlevel.openverse.resource.block.BlockTypes;
import xyz.upperlevel.openverse.resource.EntityTypeManager;

@Accessors(fluent = true)
public final class ResourceManager {

    private static ResourceManager get = new ResourceManager();

    @Getter
    private TextureManager textures = new TextureManager();

    @Getter
    private ModelManager modelManager = new ModelManager();

    @Getter
    private Programs programs = new Programs();

    @Getter
    private BlockTypes blocks = new BlockTypes();

    @Getter
    private EntityTypeManager entities = new EntityTypeManager();

    public ResourceManager() {
    }

    public void load() {
        textures.load(this);
        programs.load(this);
    }

    public void unload() {
        textures.unload();
        programs.unload();
    }

    public static ResourceManager get() {
        return get;
    }
}
