package xyz.upperlevel.openverse.resource;

import xyz.upperlevel.openverse.item.ItemTypeRegistry;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.world.entity.EntityTypeRegistry;

import java.io.File;
import java.util.logging.Logger;

/**
 * The {@link Resources} is a manager for all resource types (managers).
 * It's implemented both in client-side and server-side.
 */
public class Resources {
    protected final File folder;
    protected final Logger logger;

    private BlockTypeRegistry blockTypeRegistry;
    private ItemTypeRegistry itemTypeRegistry;
    private EntityTypeRegistry entityTypeRegistry;

    public Resources(File folder, Logger logger) {
        this.folder = folder;
        this.logger = logger;
    }

    public void init() {
        this.blockTypeRegistry = createBlockTypeRegistry();
        this.itemTypeRegistry = createItemTypeRegistry(blockTypeRegistry);
        this.entityTypeRegistry = createEntityTypeRegistry(folder, logger);
    }

    protected BlockTypeRegistry createBlockTypeRegistry() {
        return new BlockTypeRegistry();
    }

    protected ItemTypeRegistry createItemTypeRegistry(BlockTypeRegistry blockTypes) {
        return new ItemTypeRegistry(blockTypes);
    }

    protected EntityTypeRegistry createEntityTypeRegistry(File folder, Logger logger) {
        return new EntityTypeRegistry();
    }

    /**
     * Returns the {@link xyz.upperlevel.openverse.world.block.BlockType} registry
     */
    public BlockTypeRegistry blockTypes() {
        return blockTypeRegistry;
    }

    /**
     * Returns the {@link xyz.upperlevel.openverse.item.ItemType} registry
     */
    public ItemTypeRegistry itemTypes() {
        return itemTypeRegistry;
    }


    protected void onSetup() {
    }

    public void setup() {
        onSetup();
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
        return cnt;
    }

    protected void onUnload() {
    }

    public void unload() {
        onUnload();
        //entityTypeRegistry.unload();
        logger.info("All resources have been unloaded!");
    }
}
