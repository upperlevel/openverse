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
        File desktop = new File("C:/Users/Lorenzo/Desktop");
        for (int i = 0; i < 10
        00; i++) {
            manager.register(ImageIO.read(new File(desktop, "mc_chunk.png")));
            System.out.println("image: " + i);
        }
        manager.save(new File(desktop, "mine.png"));
    }
}
