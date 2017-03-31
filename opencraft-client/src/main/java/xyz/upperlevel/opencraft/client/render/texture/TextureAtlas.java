package xyz.upperlevel.opencraft.client.render.texture;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureAtlas {

    public static final TextureAtlas SIMPLE_INST = new TextureAtlas(16, 16, 1000);

    private int layerCounter = 0;

    @Getter
    private int resX, resY;

    @Getter
    private int layers;

    @Getter
    private Texture2dArray array = new Texture2dArray();

    private List<Texture> atlas_cache;

    public TextureAtlas(int resX, int resY, int layers) {
        this.resX = resX;
        this.resY = resY;
        this.layers = layers;

        array.allocate(4, GL_RGBA8, resX, resY, layers);
        atlas_cache = new ArrayList<>(layers);
    }

    public int add(@NonNull Texture texture) {
        array.load(layerCounter++, texture.getImage());
        glGenerateMipmap(GL_TEXTURE_2D_ARRAY);

        atlas_cache.add(texture);
        return layerCounter;
    }

    public void set(int layer, @NonNull Texture texture) {
        if (layer >= layers || layer < 0)
            return;
        atlas_cache.set(layer, texture);
    }

    public int getId(@NonNull Texture texture) {
        return atlas_cache.indexOf(texture);
    }
}
