package xyz.upperlevel.openverse.client.resource;

import lombok.Getter;
import xyz.upperlevel.openverse.client.resource.model.ClientModel;
import xyz.upperlevel.openverse.resource.model.ModelManager;
import xyz.upperlevel.openverse.resource.ResourceManager;

/**
 * A client resource system is able to handle textures and shader's programs.
 */
public class ClientResourceManager extends ResourceManager {

    @Getter
    private final ModelManager<ClientModel> modelManager = new ModelManager<>();

    @Getter
    private final TextureManager textureManager = new TextureManager();

    @Getter
    private final ProgramManager programManager = new ProgramManager();

    public ClientResourceManager() {
    }

    @Override
    public void load() {
        // todo load some test resources manually
    }

    @Override
    public void unload() {
        textureManager.clear();
        programManager.clear();
    }
}
