package xyz.upperlevel.openverse.resource.block;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.ResourceRegistry;

import java.io.File;
import java.util.logging.Logger;

@Getter
public class BlockTypeRegistry extends ResourceRegistry<BlockType> {
    public static final File FOLDER = new File("resources/block_types");

    private final BlockTypeLoader defaultLoader;

    public BlockTypeRegistry(Logger logger) {
        super(FOLDER, logger);
        this.defaultLoader = new BlockTypeLoader();
    }

    @Override
    protected void onFileLoad(Logger logger, File file) {
        logger.info("Loaded block type at: " + file);
    }

    @Override
    protected void onFolderLoad(Logger logger, int loaded, File folder) {
        logger.info("Loaded " + loaded + " block types in: " + folder);
    }
}
