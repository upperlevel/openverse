package xyz.upperlevel.opencraft.client.texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Textures {

    private Textures() {
    }

    private static final TextureRegistry manager = new TextureRegistry();

    public static final TextureRegistry.SpriteTexture NULL;

    static {
        NULL = manager.register(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB) {
            {
                Graphics2D g = createGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, 1, 1);
            }
        });
    }

    public static TextureRegistry manager() {
        return manager;
    }


    public static void main(String[] ar) throws IOException {
        File desktop = new File(System.getProperty("user.home"), "desktop");
        long sa = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            manager.register(ImageIO.read(new File(desktop, "lll.png")), false);
            System.out.println("image: " + i);
        }
        System.out.println("Image generated: " + (System.currentTimeMillis() - sa));
        manager.save(new File(desktop, "hello_lol.png"));
        System.out.println("Image saved");
    }
}
