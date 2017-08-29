package xyz.upperlevel.openverse.resource.model;

import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.util.file.FileUtils;

import java.io.File;

public class ModelLoader<M extends Model> implements ResourceLoader<M> {
    @Override
    public Identifier<M> load(File file) {
        return new Identifier<>(
                FileUtils.stripExtension(file),
                Model.deserialize(Config.json(file))
        );
    }
}
