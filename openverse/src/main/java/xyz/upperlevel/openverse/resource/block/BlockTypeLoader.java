package xyz.upperlevel.openverse.resource.block;

import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.util.file.FileUtils;

import java.io.File;

public class BlockTypeLoader implements ResourceLoader<BlockType> {
    @Override
    public Identifier<BlockType> load(File file) {
        return new Identifier<>(
                FileUtils.stripExtension(file),
                new BlockType(Config.json(file)));
    }
}
