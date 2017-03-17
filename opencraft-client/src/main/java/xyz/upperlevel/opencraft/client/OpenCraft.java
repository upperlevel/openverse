package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import xyz.upperlevel.gamelauncher.api.Game;
import xyz.upperlevel.opencraft.client.asset.AssetManager;
import xyz.upperlevel.opencraft.client.asset.shape.BlockCubeComponent;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShapeRegistry;
import xyz.upperlevel.opencraft.client.asset.shape.Zone3f;
import xyz.upperlevel.opencraft.client.asset.texture.Texture;
import xyz.upperlevel.opencraft.client.asset.texture.TextureRegistry;
import xyz.upperlevel.opencraft.client.render.texture.TextureBakery;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;
import xyz.upperlevel.ulge.util.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenCraft extends Game {

    private static OpenCraft get = new OpenCraft();

    static {
        {
            TextureRegistry tm = get.assetManager.getTextureRegistry();
            Texture texture;

            texture = new Texture("null_texture", new ImageContent(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB) {
                {
                    Graphics2D g = createGraphics();
                    g.setColor(java.awt.Color.WHITE);
                    g.fillRect(0, 0, 1, 1);
                }
            }));
            tm.register(texture);

            File desktop = new File(System.getProperty("user.home"), "desktop");
            try {
                texture = new Texture("something_texture", new ImageContent(ImageIO.read(new File(desktop, "hello.png"))));
                tm.register(texture);
                TextureBakery.SIMPLE_INST.register(texture);

                BufferedImage bi = ImageIO.read(new File(desktop, "grass_top.png"));
                texture = new Texture("grass_texture", new ImageContent(bi));
                tm.register(texture);
                TextureBakery.SIMPLE_INST.register(texture);
            } catch (IOException e) {
                throw new IllegalStateException("cannot load image", e);
            }
        }

        {
            BlockShapeRegistry sm = get.assetManager.getShapeRegistry();
            BlockShape shape;

            // null
            shape = new BlockShape("null_shape");
            sm.register(shape);

            // test
            shape = new BlockShape("test_shape");
            shape.add(new BlockCubeComponent(
                    new Zone3f(
                            0,
                            0,
                            0,
                            1f,
                            1f,
                            1f
                    )
            ).setColor(Color.RED)
                    .setTexture(get.assetManager.getTextureRegistry().getTexture("something_texture")));
            sm.register(shape);

            // grass
            shape = new BlockShape("grass_shape");
            shape.add(new BlockCubeComponent(
                    new Zone3f(
                            0,
                            0,
                            0,
                            1f,
                            1f,
                            1f
                    )
            ).setTexture(get.assetManager.getTextureRegistry().getTexture("grass_texture")));
            sm.register(shape);
        }
    }

    @Getter
    private AssetManager assetManager = new AssetManager();

    @Override
    public void start() {
        get = this;
    }

    @Override
    public void close() {
    }

    public static OpenCraft get() {
        return get;
    }
}
