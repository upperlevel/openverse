package xyz.upperlevel.opencraft.client.asset.texture;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;
import xyz.upperlevel.ulge.opengl.texture.TextureFormat;

import java.util.LinkedList;
import java.util.List;

public class TextureBakery {

    private int crtLayer = 0;

    @Getter
    private int resX, resY, resZ;

    @Getter
    private List<Texture> textures = new LinkedList<>();

    @Getter
    private Texture2dArray compiled = new Texture2dArray();

    public TextureBakery(int resX, int resY, int resZ) {
        this.resX = resX;
        this.resY = resY;
        this.resZ = resZ;

        compiled.allocate(4, TextureFormat.RGBA, resX, resY, resZ);
    }

    public void register(@NonNull Texture texture) {
        compiled.load(crtLayer++, texture.getImage());
        textures.add(texture);
    }

    public int getId(@NonNull Texture texture) {
        return textures.indexOf(texture);
    }
}
