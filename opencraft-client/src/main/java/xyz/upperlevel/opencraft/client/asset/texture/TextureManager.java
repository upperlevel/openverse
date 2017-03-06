package xyz.upperlevel.opencraft.client.asset.texture;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TextureManager {

    @Getter
    private int
            width = 0,
            height = 0;

    @Getter
    private BufferedImage merger;

    private Map<String, Texture> textures = new HashMap<>();

    public TextureManager() {
    }

    public TextureManager register(Texture texture) {
        return register(texture, true);
    }

    private void merge(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        if (h > height)
            height = h;
        BufferedImage result = new BufferedImage(this.width + w, this.height, BufferedImage.TYPE_INT_ARGB);

        Graphics gr = result.getGraphics();
        if (merger != null) {
            gr.drawImage(merger, 0, 0, null);
        } else
            merger = image;
        gr.drawImage(image, this.width, 0, null);

        width += w;
        merger = result;
    }

    public TextureManager register(Texture texture, boolean update) {
        merge(texture.getImage());
        textures.put(texture.getId(), texture);

        if (update)
            update();

        return this;
    }

    public Texture getTexture(String id) {
        return textures.get(id);
    }

    public Collection<Texture> getTextures() {
        return textures.values();
    }

    public void update() {
        int width = 0;
        for (Texture texture : getTextures()) {
            // the distance between the point on U axis and the origin
            float offset = ((float) width) / this.width;

            // the dimensions of the texture to draw
            float screenW = ((float) texture.getWidth()) / this.width;
            float screenH = ((float) texture.getHeight()) / height;

            texture.setScreenWidth(screenW);
            texture.setScreenHeight(screenH);
            texture.setMinU(offset);
            texture.setMaxU(offset + screenW);
            texture.setMinV(0);
            texture.setMaxV(screenH);

            width += texture.getWidth();
        }
    }

    public void print(OutputStream output) throws IOException {
        ImageIO.write(merger, "png", output);
    }

    public void print(File file) throws IOException {
        //System.out.println("attempting to print to file " + file.getPath() + " image: " + merger);
        ImageIO.write(merger, "png", file);
    }
}
