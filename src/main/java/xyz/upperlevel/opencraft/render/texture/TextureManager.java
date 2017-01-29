package xyz.upperlevel.opencraft.render.texture;

import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TextureManager {

    @Getter
    private int width = 0, height = 0;

    @Getter
    private BufferedImage output = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);

    @Getter
    private final List<TextureFragment> fragments = new ArrayList<>();

    public TextureManager register(int width, int height, BufferedImage texture) {
        BufferedImage result = new BufferedImage(this.width + width, this.height + height, BufferedImage.TYPE_INT_ARGB);

        Graphics gr = result.getGraphics();
        gr.drawImage(output, 0, 0, null);
        gr.drawImage(texture, this.width, this.height, null);

        this.width += width;

        output = result;

        fragments.add(new TextureFragment(fragments.size(), this, width, height));
        update();
        return this;
    }

    public TextureFragment getFragment(int id) {
        return fragments.get(id);
    }

    public void update() {
        int width = 0;
        for (TextureFragment fragment : fragments) {
            float currentWidth = ((float) width) / this.width;
            float realWidth = ((float) fragment.width) / this.width;
            float realHeight = ((float) fragment.height) / height;

            fragment.realWidth = realWidth;
            fragment.realHeight = realHeight;
            fragment.minU = currentWidth;
            fragment.maxU = currentWidth + realWidth;
            fragment.minV = 0;
            fragment.maxV = realHeight;

            width += fragment.width;
        }
    }
}
