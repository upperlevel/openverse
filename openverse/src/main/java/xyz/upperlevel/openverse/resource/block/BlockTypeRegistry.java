package xyz.upperlevel.openverse.resource.block;

import lombok.Getter;
import xyz.upperlevel.hermes.util.DynamicArray;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.ResourceRegistry;

import java.io.File;
import java.util.logging.Logger;

@Getter
public class BlockTypeRegistry extends ResourceRegistry<BlockType> {
    private final BlockTypeLoader defaultLoader;
    private int nextId = 1;
    private DynamicArray<BlockType> idRegistry = new DynamicArray<>(256);

    public BlockTypeRegistry(File folder, Logger logger) {
        super(new File(folder, "block_types"), logger);
        this.defaultLoader = createLoader();
    }

    protected BlockTypeLoader createLoader() {
        return new BlockTypeLoader();
    }

    @Override
    protected void onFileLoad(Logger logger, File file) {
        logger.info("Loaded block type at: " + file);
    }

    @Override
    protected void onFolderLoad(Logger logger, int loaded, File folder) {
        logger.info("Loaded " + loaded + " block types in: " + folder);
    }

    public void setId(BlockType type, int id) {
        if (type != null) {
            Openverse.logger().fine("Registered " + type.getId() + " as " + id);
            if (type.getRawId() != 0) {
                Openverse.logger().warning("Overriding id of " + type.getId() + ", old: " + type.getRawId() + ", new: " + id);
            }
            type.setRawId(id);
        }
        if (idRegistry.get(id) != null) {
            Openverse.logger().warning("Overriding type of " + id + " from " + idRegistry.get(id) + " to " + type);
        }
        idRegistry.set(id, type);
    }

    public BlockType entry(int id) {
        return idRegistry.get(id);
    }

    public void registerId(BlockType type) {
        setId(type, nextId++);
    }
}
