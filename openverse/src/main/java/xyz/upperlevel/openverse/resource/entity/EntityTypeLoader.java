package xyz.upperlevel.openverse.resource.entity;

import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.util.file.FileUtils;

import java.io.File;

public class EntityTypeLoader implements ResourceLoader<EntityType> {
    @Override
    public Identifier<EntityType> load(File file) {
        return new Identifier<>(
                FileUtils.stripExtension(file),
                new EntityType(Config.json(file))
        );
    }
}
