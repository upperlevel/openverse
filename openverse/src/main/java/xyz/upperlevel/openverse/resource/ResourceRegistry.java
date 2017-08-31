package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;

import java.io.File;
import java.util.logging.Logger;

@Getter
public abstract class ResourceRegistry<E> extends Registry<E> {
    private final Logger logger;
    private final File defaultFolder;

    public ResourceRegistry(File defaultFolder, Logger logger) {
        this.defaultFolder = defaultFolder;
        this.logger = logger;
    }

    /**
     * Creates default folder.
     */
    public void setup() {
        if (defaultFolder.exists()) {
            logger.info(defaultFolder + " already exists, skipping it!");
        } else {
            if (!defaultFolder.mkdirs()) {
                logger.warning(defaultFolder + " hasn't been created.");
            } else {
                logger.info(defaultFolder + " created successfully!");
            }
        }
    }

    protected void onUnload(E entry) {
    }

    public void unload() {
        entries().forEach(this::onUnload);
        clear();
    }

    /**
     * Loader used by default by this manager to load resources.
     */
    protected abstract ResourceLoader<E> getDefaultLoader();

    public boolean loadFile(File file) {
        return loadFile(getDefaultLoader(), file);
    }

    public boolean loadFile(ResourceLoader<E> loader, File file) {
        Openverse.logger().info("File: " + file);
        if (file.exists() && !file.isDirectory()) {
            Identifier<E> res = loader.load(file);
            register(res);
            onFileLoad(logger, file);
            Openverse.logger().info("LOADED!");
            return true;
        }
        return false;
    }

    protected abstract void onFileLoad(Logger logger, File file);


    public int loadFolder() {
        return loadFolder(getDefaultLoader(), getDefaultFolder());
    }

    public int loadFolder(ResourceLoader<E> loader) {
        return loadFolder(loader, getDefaultFolder());
    }

    public int loadFolder(File folder) {
        return loadFolder(getDefaultLoader(), folder);
    }

    public int loadFolder(ResourceLoader<E> loader, File folder) {
        int cnt = 0;
        Openverse.logger().info("loading " + folder);
        if (folder.exists()) {
            Openverse.logger().info(folder + " exists");
            if (folder.isDirectory()) {
                Openverse.logger().info(folder + " is dir");
                File[] files = folder.listFiles();
                if (files != null) {
                    Openverse.logger().info(folder + " seems to have files...");
                    for (File file : files) {
                        Openverse.logger().info(folder + " sub-file: " + file);
                        if (loadFile(loader, file))
                            cnt++;
                    }
                }
                onFolderLoad(logger, cnt, folder);
            } else
                throw new IllegalStateException("Given file is not a folder!");
        }
        return cnt;
    }

    protected abstract void onFolderLoad(Logger logger, int loaded, File folder);
}
