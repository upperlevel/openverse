package xyz.upperlevel.openverse.client.render.texture;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.client.resource.texture.Texture;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureContainer {

    private int layerCounter = 0;

    @Getter
    private int width, height, layers;

    @Getter
    private Texture2dArray array = new Texture2dArray();
    private List<Texture> arrayCache;

    public TextureContainer(int width, int height, int layers) {
        this.width = width;
        this.height = height;
        this.layers = layers;

        array.allocate(4, GL_RGBA8, width, height, layers);
        arrayCache = new ArrayList<>(layers);
    }

    public int add(@NonNull Texture texture) {
        array.load(layerCounter++, texture.getImage());
        glGenerateMipmap(GL_TEXTURE_2D_ARRAY);

        arrayCache.add(texture);
        return layerCounter;
    }

    public void set(int layer, @NonNull Texture texture) {
        if (layer >= layers || layer < 0)
            return;
        array.load(layer, texture.getImage());
        arrayCache.set(layer, texture);
    }

    public int getId(@NonNull Texture texture) {
        return arrayCache.indexOf(texture);
    }

    public List<Texture> get() {
        return arrayCache;
    }
}