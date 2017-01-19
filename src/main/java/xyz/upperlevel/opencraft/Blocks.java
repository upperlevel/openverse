package xyz.upperlevel.opencraft;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.upperlevel.graphicengine.api.opengl.texture.loader.TextureLoaderManager;
import xyz.upperlevel.graphicengine.api.util.Color;
import xyz.upperlevel.graphicengine.api.opengl.texture.Texture;
import xyz.upperlevel.opencraft.world.block.BlockId;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Blocks {

    public static final BlockRegistry REGISTRY = new BlockRegistry();

    public static final BlockId WOOD;

    static {
        TextureLoaderManager loader = TextureLoaderManager.DEFAULT;
        Texture texture, specularMap;

        texture = new Texture();
        texture.setContent(loader.load(new File("C:/Users/Lorenzo/Desktop/textures/container2.png")));

        specularMap = new Texture();
        specularMap.setContent(loader.load(new File("C:/Users/Lorenzo/Desktop/textures/container2_specular.png")));

        WOOD = new BlockId(new Material(texture, specularMap, 32f), Color.WHITE);
        REGISTRY.register(WOOD);
    }
}
