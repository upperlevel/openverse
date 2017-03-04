package xyz.upperlevel.opencraft.client.texture;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureRegistry {

    @Getter
    private int width = 0, height = 0;

    private BufferedImage merger;

    @Getter
    private final List<SpriteTexture> sprites = new ArrayList<>();

    public TextureRegistry() {
    }

    public SpriteTexture register(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        if (h > height)
            height = h;
        BufferedImage result = new BufferedImage(this.width + w, this.height, BufferedImage.TYPE_INT_ARGB);

        if (merger != null) {
            Graphics gr = result.getGraphics();
            gr.drawImage(merger, 0, 0, null);
            gr.drawImage(image, this.width, 0, null);
        } else
            merger = image;

        width += w;

        // updates output buffer
        merger = result;

        SpriteTexture tex = new SpriteTexture(w, h);
        sprites.add(tex);
        update();
        return tex;
    }

    public SpriteTexture getSprite(int id) {
        return sprites.get(id);
    }

    public void update() {
        int width = 0;
        for (SpriteTexture sprite : sprites) {
            // the distance between the point on U axis and the origin
            float currentWidth = ((float) width) / this.width;

            // the dimensions of the texture to draw
            float realWidth  = ((float) sprite.getWidth()) / this.width;
            float realHeight = ((float) sprite.getHeight()) / height;

            sprite.realWidth = realWidth;
            sprite.realHeight = realHeight;
            sprite.minU = currentWidth;
            sprite.maxU = currentWidth + realWidth;
            sprite.minV = 0;
            sprite.maxV = realHeight;

            width += sprite.getWidth();
        }
    }

    public void save(File file) throws IOException {
        ImageIO.write(merger, "png", file);
    }

    public static class SpriteTexture {

        @Getter
        private int width, height;

        @Getter
        @Setter
        private float realWidth, realHeight;

        @Getter
        @Setter
        private float minU, minV, maxU, maxV;

        private SpriteTexture(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString() {
            return "{minU: " + minU + " " + " minV: " + minV + " maxU: " + maxU + " maxV: " + maxV + "}";
        }
    }
}
