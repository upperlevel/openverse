package xyz.upperlevel.openverse.client.resource;

import lombok.Getter;
import xyz.upperlevel.openverse.client.resource.model.ClientModel;
import xyz.upperlevel.openverse.client.resource.model.ClientModelManager;
import xyz.upperlevel.openverse.resource.model.ModelManager;
import xyz.upperlevel.openverse.resource.ResourceManager;

/**
 * A client resource system is able to handle textures and shader's programs.
 */
public class ClientResourceManager extends ResourceManager {

    @Getter
    private final ClientModelManager modelManager;

    @Getter
    private final TextureManager textureManager;

    @Getter
    private final ShaderProgramManager shaderProgramManager;

    public ClientResourceManager() {
        modelManager = new ClientModelManager();
        textureManager = new TextureManager(16, 16);
        shaderProgramManager = new ShaderProgramManager();
    }

    @Override
    public void load() {
        // todo load some test resources manually
    }

    @Override
    public void unload() {
        textureManager.clear();
        shaderProgramManager.clear();
    }
}
