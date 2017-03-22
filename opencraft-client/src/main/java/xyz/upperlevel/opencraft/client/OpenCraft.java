package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.asset.AssetManager;
import xyz.upperlevel.opencraft.client.asset.shape.BlockCubeComponent;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShapeRegistry;
import xyz.upperlevel.opencraft.client.asset.shape.Zone3f;
import xyz.upperlevel.opencraft.client.asset.texture.Texture;
import xyz.upperlevel.opencraft.client.asset.texture.TextureRegistry;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;
import xyz.upperlevel.ulge.util.Color;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OpenCraft {

    private static OpenCraft get = new OpenCraft();

    static {
        {
            TextureRegistry tm = get.assetManager.getTextureRegistry();
            Texture texture;

            texture = new Texture("null", new ImageContent(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB) {
                {
                    Graphics2D g = createGraphics();
                    g.setColor(java.awt.Color.WHITE);
                    g.fillRect(0, 0, 1, 1);
                }
            }));
            tm.register(texture);

            tm.registerDirectory(OpenCraft.class.getClassLoader().getResource("tex"));
        }

        {
            BlockShapeRegistry sm = get.assetManager.getShapeRegistry();
            BlockShape shape;

            // null
            shape = new BlockShape("null_shape");
            sm.register(shape);

            // test
            shape = new BlockShape("test_shape");
            shape.add(
                    new BlockCubeComponent(
                            new Zone3f(
                                    0,
                                    0,
                                    0,
                                    1f,
                                    1f,
                                    1f
                            )
                    )
                            .setColor(Color.RED)
                            .setTexture(get.assetManager.getTextureRegistry().getTexture("something"))
            );
            sm.register(shape);

            // grass
            shape = new BlockShape("grass_shape");
            shape.add(
                    new BlockCubeComponent(
                            new Zone3f(
                                    0,
                                    0,
                                    0,
                                    1f,
                                    1f,
                                    1f
                            )
                    ).setTexture(get.assetManager.getTextureRegistry().getTexture("grass"))
            );
            sm.register(shape);
        }
    }

    @Getter
    private AssetManager assetManager = new AssetManager();

    public static OpenCraft get() {
        return get;
    }
}
