package xyz.upperlevel.openverse.client.resource.texture;

import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.Resources;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;
import xyz.upperlevel.ulge.util.FileUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureLoader implements ResourceLoader<Texture> {
    @Override
    public Identifier<Texture> load(File file) {
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load image at: " + file, e);
        }
        return new Identifier<>(FileUtil.stripExtension(file), new Texture(new ImageContent(image)));
    }
}
