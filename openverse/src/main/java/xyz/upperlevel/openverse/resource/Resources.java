package xyz.upperlevel.openverse.resource;

import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;
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
public class Resources {
    protected final File folder;
    protected final Logger logger;

    private final BlockTypeRegistry blockTypeRegistry;
    private final EntityTypeRegistry entityTypeRegistry;
    private final ShapeTypeRegistry<? extends ShapeType<?>> shapeTypeRegistry;
    private final ModelRegistry<? extends Model<?>> modelRegistry;

    public Resources(File folder, Logger logger) {
        this.folder = folder;
        this.logger = logger;
        this.blockTypeRegistry = createBlockTypeRegistry();
        this.entityTypeRegistry = createEntityTypeRegistry(folder, logger);
        this.shapeTypeRegistry = createShapeTypeRegistry(folder, logger);
        this.modelRegistry = createModelRegistry(folder, logger);
    }

    protected BlockTypeRegistry createBlockTypeRegistry() {
        return new BlockTypeRegistry();
    }

    protected EntityTypeRegistry createEntityTypeRegistry(File folder, Logger logger) {
        return new EntityTypeRegistry(folder, logger);
    }

    protected ShapeTypeRegistry<? extends ShapeType<?>> createShapeTypeRegistry(File fodler, Logger logger) {
        return new ShapeTypeRegistry<>();
    }

    protected ModelRegistry<? extends Model<?>> createModelRegistry(File folder, Logger logger) {
        return new ModelRegistry<>(folder, logger);
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
        cnt += entityTypeRegistry.loadFolder();
        return cnt;
    }

    protected void onUnload() {
    }

    public void unload() {
        models().unload();
        entityTypeRegistry.unload();
        onUnload();
        //entityTypeRegistry.unload();
        logger.info("All resources have been unloaded!");
    }
}
