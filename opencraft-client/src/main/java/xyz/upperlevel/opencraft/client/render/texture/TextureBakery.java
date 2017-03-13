package xyz.upperlevel.opencraft.client.render.texture;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.opencraft.client.asset.texture.Texture;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureBakery {

    public static final TextureBakery SIMPLE_INST = new TextureBakery(16, 16);

    private int crtLayer = 0;

    @Getter
    private int resolutionX, resolutionY;

    @Getter
    private List<Texture> textures = new LinkedList<>();

    @Getter
    private Texture2dArray compiled = new Texture2dArray();

    public TextureBakery(int resolutionX, int resolutionY) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;

        compiled.allocate(4, GL_RGBA8, resolutionX, resolutionY, 2);
    }

    public void register(@NonNull Texture texture) {
        compiled.load(crtLayer++, texture.getImage());
        glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
        textures.add(texture);
    }

    public int getId(@NonNull Texture texture) {
        return textures.indexOf(texture);
    }
}
