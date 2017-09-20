package xyz.upperlevel.openverse.client.render.block;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public final class TextureBakery {
    public static final int NULL_LAYER = 0;
    public static final ImageContent NULL = new ImageContent(1, 1, (ByteBuffer) BufferUtils.createByteBuffer(4)
            .put(new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 255})
            .flip()
    );

    private static Texture2dArray textureArray;
    private static Map<Path, ImageContent> registered = new HashMap<>();
    private static Map<Path, Integer> layers;

    public static void load(Path path) {
        try {
            register(path, new ImageContent(ImageIO.read(path.toFile())));
        } catch (IOException ignored) {
        }
    }

    public static void register(Path path, ImageContent image) {
        registered.put(path, image);
    }

    public static void bake() {
        layers = new HashMap<>();
        textureArray = new Texture2dArray();
        textureArray.allocate(4, GL_RGBA8, 16, 16, 1 + registered.size());
        textureArray.load(0, NULL);
        int layer = 1;
        for (Map.Entry<Path, ImageContent> entry : registered.entrySet()) {
            if (layers.put(entry.getKey(), layer + 1) == null) {
                layer++;
                textureArray.load(layer, entry.getValue());
                glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
                // todo texture  parameters
            }
        }
    }

    public static void bind() {
        if (textureArray != null) {
            textureArray.bind();
        }
    }

    public static int getLayer(Path path) {
        return layers.get(path);
    }

    public static void destroy() {
        registered.clear();
        registered = null;
        textureArray.destroy();
        textureArray = null;
    }
}
