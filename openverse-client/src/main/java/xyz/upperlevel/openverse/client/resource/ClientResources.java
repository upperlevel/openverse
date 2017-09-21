package xyz.upperlevel.openverse.client.resource;

import xyz.upperlevel.openverse.client.render.block.TextureBakery;
import xyz.upperlevel.openverse.client.resource.program.ProgramRegistry;
import xyz.upperlevel.openverse.client.resource.shader.ShaderRegistry;
import xyz.upperlevel.openverse.resource.Resources;

import java.io.File;
import java.util.logging.Logger;

/**
 * The {@link ClientResources} is a manager for all client resources.
 * Differently from the {@link Resources} it has a {@link TextureBakery}, {@link ShaderRegistry} and a {@link ProgramRegistry}
 * classes that are needed only in client-side (and other different implementations).
 */
public class ClientResources extends Resources {
    private final ShaderRegistry shaderRegistry;
    private final ProgramRegistry programRegistry;

    /**
     * The constructor of {@link ClientResources} initializes all sub resource managers.
     */
    public ClientResources(Logger logger) {
        super(new File("client/resources"), logger);
        this.shaderRegistry = new ShaderRegistry(folder, logger);
        this.programRegistry = new ProgramRegistry(folder, logger);
    }

    /**
     * Returns the {@link ShaderRegistry} object.
     */
    public ShaderRegistry shaders() {
        return shaderRegistry;
    }

    /**
     * Returns the {@link ProgramRegistry} object.
     */
    public ProgramRegistry programs() {
        return programRegistry;
    }


    @Override
    protected ClientBlockTypeRegistry createBlockTypeRegistry() {
        return new ClientBlockTypeRegistry();
    }

    //TODO: come on it's the only freaking class that needs to do this hack, and it's not even necessary!
    @Override
    public ClientBlockTypeRegistry blockTypes() {
        return (ClientBlockTypeRegistry) super.blockTypes();
    }

    @Override
    protected void onSetup() {
        shaderRegistry.setup();
        programRegistry.setup();
    }

    @Override
    protected int onLoad() {
        int cnt = 0;
        cnt += shaderRegistry.loadFolder();
        cnt += programRegistry.loadFolder();
        return cnt;
    }

    @Override
    protected void onUnload() {
        shaderRegistry.unload();
        programRegistry.unload();
    }
}
