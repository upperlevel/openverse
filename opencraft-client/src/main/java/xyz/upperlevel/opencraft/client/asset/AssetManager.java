package xyz.upperlevel.opencraft.client.asset;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShapeManager;
import xyz.upperlevel.opencraft.client.asset.texture.TextureManager;

public class AssetManager {

    @Getter
    private BlockShapeManager shapeManager = new BlockShapeManager();

    @Getter
    private TextureManager textureManager  = new TextureManager();

    public AssetManager() {
    }
}