package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.render.model.ModelBakery;
import xyz.upperlevel.opencraft.client.render.texture.TextureAtlas;

public class RenderContext {

    @Getter
    private ModelBakery modelBakery = new ModelBakery();

    @Getter
    private TextureAtlas textureBakery = new TextureAtlas(16, 16, 100);

    public RenderContext() {
    }
}