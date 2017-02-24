package xyz.upperlevel.opencraft.client.texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Textures {

    private Textures() {
    }

    private static final TextureManager manager = new TextureManager();

    public static final TextureFragment NULL;

    static {
        NULL = manager.register(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB) {
            {
                Graphics2D g = createGraphics();
                g.setColor(Color.RED);
                g.fillRect(0, 0, 1, 1);
            }
        });
    }

    public static TextureManager manager() {
        return manager;
    }
}
