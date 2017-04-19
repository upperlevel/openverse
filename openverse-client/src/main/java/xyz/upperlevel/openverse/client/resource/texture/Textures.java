package xyz.upperlevel.openverse.client.resource.texture;

import lombok.NonNull;
import xyz.upperlevel.openverse.client.render.texture.TextureContainer;
import xyz.upperlevel.openverse.client.resource.Resources;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Textures {

    private Map<String, Texture> textures = new HashMap<>();

    public Textures() {
    }

    public void load(Resources resources) {
        // todo
    }

    public void unload() {
        textures.clear();
    }

    public void register(@NonNull Texture texture) {
        textures.put(texture.getId(), texture);
    }

    public int registerDirectory(@NonNull URL dir) {
        try {
            return registerDirectory(new File(dir.toURI()));
        } catch (URISyntaxException e) {
            return 0;
        }
    }

    public int registerDirectory(@NonNull File dir) {
        File[] files = dir.listFiles();
        if (files == null || files.length == 0)
            return 0;

        int counter = 0;

        for (File file : files) {
            if (file.isFile()) {
                BufferedImage image;
                try {
                    image = ImageIO.read(file);
                } catch (IOException e) {
                    System.err.println("Error loading textures from " + dir);
                    e.printStackTrace();
                    image = null;
                }
                if (image == null)
                    continue;

                Texture texture = new Texture(removeExtension(file.getName()), new ImageContent(image));
                register(texture);
                TextureContainer.SIMPLE_INST.add(texture);
                counter++;
            }
        }

        return counter;
    }

    public Texture get(String id) {
        return textures.get(id);
    }

    public Collection<Texture> get() {
        return textures.values();
    }

    public void unregister(String id) {
        textures.remove(id);
    }

    public void unregister(@NonNull Texture texture) {
        unregister(texture.getId());
    }

    private static String removeExtension(String str) {//I'm ashamed to write a so-commonly used method in a class like this
        if (str == null) return null;
        int pos = str.lastIndexOf(".");
        return (pos == -1) ? str : str.substring(0, pos);
    }
}
