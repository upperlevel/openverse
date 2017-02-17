package xyz.upperlevel.opencraft.renderer.texture;

import lombok.Getter;
import xyz.upperlevel.ulge.opengl.texture.Texture2D;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureManager {

    @Getter
    private int width = 0, height = 0;

    private BufferedImage merger;

    @Getter
    private final List<TextureFragment> fragments = new ArrayList<>();

    public TextureManager() {
    }

    public TextureFragment register(BufferedImage image) {
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

        TextureFragment tex = new TextureFragment(fragments.size(), this, w, h);

        // updates fragment data
        fragments.add(tex);
        updateFragments();
        return tex;
    }

    public TextureFragment getFragment(int id) {
        return fragments.get(id);
    }

    public Texture2D getOutput() {
        Texture2D r = new Texture2D();
        r.loadImage(new ImageContent(merger, false));
        return r;
    }

    private void updateFragments() {
        int width = 0;
        for (TextureFragment fragment : fragments) {
            // the distance between the point on U axis and the origin
            float currentWidth = ((float) width) / this.width;

            // the dimensions of the texture to draw
            float realWidth = ((float) fragment.getWidth()) / this.width;
            float realHeight = ((float) fragment.getHeight()) / height;

            fragment.realWidth = realWidth;
            fragment.realHeight = realHeight;
            fragment.minU = currentWidth;
            fragment.maxU = currentWidth + realWidth;
            fragment.minV = 0;
            fragment.maxV = realHeight;

            width += fragment.getWidth();
        }
    }

    public void save(File file) throws IOException {
        ImageIO.write(merger, "png", file);
    }
}
