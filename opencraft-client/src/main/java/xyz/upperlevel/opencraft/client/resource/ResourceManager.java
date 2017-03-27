package xyz.upperlevel.opencraft.client.resource;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.resource.model.ModelManager;
import xyz.upperlevel.opencraft.client.resource.texture.TextureManager;

public class ResourceManager {

    @Getter
    private TextureManager textures = new TextureManager();

    @Getter
    private ModelManager models = new ModelManager();

    public ResourceManager() {
    }
}
