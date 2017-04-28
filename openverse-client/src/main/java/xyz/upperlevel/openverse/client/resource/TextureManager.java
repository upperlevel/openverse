package xyz.upperlevel.openverse.client.resource;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;
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

/**
 * This class handles texture instances by indexing them using an id.
 */
@RequiredArgsConstructor
public class TextureManager {

    @Getter
    private final Map<String, Texture> texturesMap = new HashMap<>();

    @Getter
    private final int textureWidth, textureHeight;

    public void register(@NonNull Texture texture) {
        texturesMap.put(texture.getId(), texture);
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
                    System.err.println("Error loading texturesMap from " + dir);
                    e.printStackTrace();
                    image = null;
                }
                if (image == null)
                    continue;

                Texture texture = new Texture(removeExtension(file.getName()), new ImageContent(image));
                register(texture);
                counter++;
            }
        }

        return counter;
    }

    public Texture get(String id) {
        return texturesMap.get(id);
    }

    public Collection<Texture> get() {
        return texturesMap.values();
    }

    public void unregister(String id) {
        texturesMap.remove(id);
    }

    public void unregister(@NonNull Texture texture) {
        unregister(texture.getId());
    }

    public void clear() {
        texturesMap.clear();
    }

    private static String removeExtension(String str) {//I'm ashamed to write a so-commonly used method in a class like this
        if (str == null) return null;
        int pos = str.lastIndexOf(".");
        return (pos == -1) ? str : str.substring(0, pos);
    }
}
