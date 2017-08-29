package xyz.upperlevel.openverse.resource.model;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.ResourceRegistry;

import java.io.File;
import java.util.logging.Logger;

@Getter
public class ModelRegistry<M extends Model> extends ResourceRegistry<M> {
    public static final File FOLDER = new File("resources/models");

    private final ModelLoader<M> defaultLoader = new ModelLoader<>(); // not static for template

    public ModelRegistry(Logger logger) {
        super(FOLDER, logger);
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
