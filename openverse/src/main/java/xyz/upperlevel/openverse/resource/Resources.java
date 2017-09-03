package xyz.upperlevel.openverse.resource;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.resource.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.resource.entity.EntityTypeRegistry;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.resource.model.ModelRegistry;
import xyz.upperlevel.openverse.resource.model.shape.ShapeType;
import xyz.upperlevel.openverse.resource.model.shape.ShapeTypeRegistry;

import java.io.File;
import java.util.logging.Logger;

/**
 * The {@link Resources} is a manager for all resource types (managers).
 * It's implemented both in client-side and server-side.
 */
@RequiredArgsConstructor
public class Resources {
    protected final File folder;
    protected final Logger logger;

    private final BlockTypeRegistry blockTypeRegistry;
    private final EntityTypeRegistry entityTypeRegistry;
    // overridden by client
    private final ShapeTypeRegistry<ShapeType> shapeTypeRegistry;
    private final ModelRegistry<Model> modelRegistry;

    public Resources(File folder, Logger logger) {
        this.folder = folder;
        this.logger = logger;
        // -- these have different implementation
        this.shapeTypeRegistry = new ShapeTypeRegistry<>();
        this.modelRegistry = new ModelRegistry<>(folder, logger);
        // --
        this.blockTypeRegistry = new BlockTypeRegistry(folder, logger);
        this.entityTypeRegistry = new EntityTypeRegistry(folder, logger);
    }

    /**
     * Returns the {@link BlockTypeRegistry} object.
     */
    public BlockTypeRegistry blockTypes() {
        return blockTypeRegistry;
    }

    /**
     * Returns the {@link ShapeTypeRegistry} object.
     */
    public ShapeTypeRegistry<? extends ShapeType> shapes() {
        return shapeTypeRegistry;
    }

    /**
     * Returns the {@link ModelRegistry} object.
     */
    public ModelRegistry<? extends Model> models() {
        return modelRegistry;
    }

    protected void onSetup() {
    }

    public void setup() {
        onSetup();
        blockTypeRegistry.setup();
        entityTypeRegistry.setup();
    }

    protected int onLoad() {
        return 0;
    }

    /**
     * Loads all resources from their default folders with their default loaders.
     * Usually at: <b>"resources/%resource_names%"</b>
     */
    public int load() {
        int cnt = 0;
        cnt += onLoad();
        cnt += models().loadFolder();
        cnt += blockTypeRegistry.loadFolder();
        cnt += entityTypeRegistry.loadFolder();
        return cnt;
    }

    protected void onUnload() {
    }

    public void unload() {
        models().unload();
        blockTypeRegistry.unload();
        entityTypeRegistry.unload();
        onUnload();
        //entityTypeRegistry.unload();
        logger.info("All resources have been unloaded!");
    }
}
