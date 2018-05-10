package xyz.upperlevel.openverse.client.resource.program;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.resource.ResourceRegistry;
import xyz.upperlevel.ulge.opengl.shader.Program;

import java.io.File;
import java.util.logging.Logger;

/**
 * The program manager contains a list of all programs.
 * It may be used after have set a context for OpenGL.
 */
@Getter
public class ProgramRegistry extends ResourceRegistry<Program> {
    public static final ProgramLoader LOADER = new ProgramLoader();

    public ProgramRegistry(File folder, Logger logger) {
        super(new File(folder, "programs"), logger);
    }

    @Override
    protected ResourceLoader<Program> getDefaultLoader() {
        return LOADER;
    }

    @Override
    protected void onFileLoad(Logger logger, File file) {
        logger.info("Loaded program in: " + file);
    }

    @Override
    protected void onFolderLoad(Logger logger, int loaded, File folder) {
        logger.info("Loaded " + loaded + " programs in: " + folder);
    }

    @Override
    protected void onUnload(Program entry) {
        entry.unuse();// ensure
        entry.destroy();
    }
}