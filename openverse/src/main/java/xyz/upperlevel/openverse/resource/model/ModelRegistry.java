package xyz.upperlevel.openverse.resource.model;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.resource.ResourceRegistry;

import java.io.File;
import java.util.logging.Logger;

public class ModelRegistry<M extends Model> extends ResourceRegistry<M> {
    private final ModelLoader<M> defaultLoader = new ModelLoader<>(); // not static for template

    public ModelRegistry(File folder, Logger logger) {
        super(new File(folder, "models"), logger);
    }

    @Override
    protected ModelLoader<M> getDefaultLoader() {
        return defaultLoader;
    }

    @Override
    protected void onFileLoad(Logger logger, File file) {
        logger.info("Loaded model at: " + file);
    }

    @Override
    protected void onFolderLoad(Logger logger, int loaded, File folder) {
        logger.info("Loaded " + loaded + " models in: " + folder);
    }
}
