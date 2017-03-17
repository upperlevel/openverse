package xyz.upperlevel.opencraft.client.asset;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShapeRegistry;
import xyz.upperlevel.opencraft.client.asset.texture.TextureRegistry;

public class AssetManager {

    @Getter
    private BlockShapeRegistry shapeRegistry = new BlockShapeRegistry();

    @Getter
    private TextureRegistry textureRegistry = new TextureRegistry();

    public AssetManager() {
    }
}