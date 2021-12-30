package xyz.upperlevel.openverse.client.resource;

import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.block.TextureBakery;
import xyz.upperlevel.openverse.client.resource.program.ProgramRegistry;
import xyz.upperlevel.openverse.client.resource.shader.ShaderRegistry;
import xyz.upperlevel.openverse.resource.Resources;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

import java.io.File;
import java.util.logging.Logger;

/**
 * The {@link ClientResources} is a manager for all client resources.
 * Differently from the {@link Resources} it has a {@link TextureBakery}, {@link ShaderRegistry} and a {@link ProgramRegistry}
 * classes that are needed only in client-side (and other different implementations).
 */
public class ClientResources extends Resources {
    private ShaderRegistry shaderRegistry;
    private ProgramRegistry programRegistry;

    /**
     * The constructor of {@link ClientResources} initializes all sub resource managers.
     */
    public ClientResources(OpenverseClient client, Logger logger) {
        super(client, new File("resources"), logger);
    }

    @Override
    public void init() {
        super.init();
        this.shaderRegistry  = new ShaderRegistry(folder, logger);
        this.programRegistry = new ProgramRegistry((OpenverseClient) getModule(), folder, logger);
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
        return new ClientBlockTypeRegistry((OpenverseClient) getModule());
    }

    @Override
    protected ClientItemTypeRegistry createItemTypeRegistry(BlockTypeRegistry blockTypes) {
        return new ClientItemTypeRegistry((OpenverseClient) getModule(), blockTypes);
    }

    //TODO: come on it's the only freaking class that needs to do this hack, and it's not even necessary!
    @Override
    public ClientBlockTypeRegistry blockTypes() {
        return (ClientBlockTypeRegistry) super.blockTypes();
    }

    @Override
    public ClientItemTypeRegistry itemTypes() {
        return (ClientItemTypeRegistry) super.itemTypes();
    }

    @Override
    protected void onSetup() {
        shaderRegistry.setup();
        programRegistry.setup();
    }

    @Override
    protected int onLoad() {
        int shadersCount = shaderRegistry.loadFolder();
        int programCount = programRegistry.loadFolder();

        logger.info(String.format("Loaded %d shaders", shadersCount));
        logger.info(String.format("Loaded %d programs", shadersCount));

        return shadersCount +  programCount;
    }

    @Override
    protected void onUnload() {
        shaderRegistry.unload();
        programRegistry.unload();
    }
}
