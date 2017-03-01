package xyz.upperlevel.opencraft.client.block;

import xyz.upperlevel.opencraft.client.texture.TextureManager;
import xyz.upperlevel.opencraft.client.texture.Textures;
import xyz.upperlevel.ulge.util.Color;
import javax.imageio.ImageIO;
import java.io.IOException;

public class TestBlockShape {

    public static final BlockShape inst = new BlockShape();

    static {
        TextureManager texMan = Textures.manager();
        try {
            texMan.register(ImageIO.read(TestBlockShape.class.getClassLoader().getResourceAsStream("textures/dirt2.png")));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        ShapeComponent comp = new ShapeComponent()
                .setColor(Color.RED);
        comp.getFace(BlockFacePosition.UP)
                .setTexture(texMan.getFragment(0));
        inst.addComponent(comp);
    }
}