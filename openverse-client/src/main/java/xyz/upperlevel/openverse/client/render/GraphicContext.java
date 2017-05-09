package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.world.RenderUniverse;

public class GraphicContext {

    @Getter
    private final OpenverseClient client;

    @Getter
    private final RenderUniverse universe;

    @Getter
    private final ModelCompilerManager modelCompilerManager;

    @Getter
    private final TextureBakery textureBakery;

    public GraphicContext(OpenverseClient client) {
        this.client = client;

        universe = new RenderUniverse(client);
        modelCompilerManager = new ModelCompilerManager();
        textureBakery = new TextureBakery(client);
    }
}
