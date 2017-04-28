package xyz.upperlevel.openverse.client.resource;

import lombok.Getter;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_RGBA8;

/**
 * This class wraps a texture array.
 */
public class TextureBakery {

    @Getter
    private final Texture2dArray array;

    // the resolution dimensions are the max dim a texture in this array can have
    @Getter
    private final int resolutionWidth, resolutionHeight;

    // just a cache for layers
    private final List<Texture> layers = new ArrayList<>();

    public TextureBakery(int resolutionWidth, int resolutionHeight) {
        this.resolutionWidth  = resolutionWidth;
        this.resolutionHeight = resolutionHeight;

        array = new Texture2dArray();
    }

    public void load(TextureManager manager) {
        array.allocate(4, GL_RGBA8, resolutionWidth, resolutionHeight, manager.get().size());
        int i = 0;
        for (Texture texture : manager.get()) {
            array.load(i++, texture.getImage());
            layers.add(texture);
        }
    }

    public int getLayer(Texture texture) {
        return layers.indexOf(texture);
    }

    public void destroy() {
        array.destroy();
    }
}
