package xyz.upperlevel.opencraft.client.texture;

import lombok.Getter;

public class TextureFragment {

    @Getter
    private final int id;

    @Getter
    private TextureManager manager;

    @Getter
    private int width, height;

    @Getter
    float realWidth, realHeight;

    @Getter
    float minU, minV, maxU, maxV;

    TextureFragment(int id, TextureManager manager, int width, int height) {
        this.id = id;
        this.manager = manager;
        this.width = width;
        this.height = height;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TextureFragment && ((TextureFragment) object).id == id;
    }

    @Override
    public String toString() {
        return "{minU: " + minU + " " + " minV: " + minV + " maxU: " + maxU + " maxV: " + maxV + "}";
    }
}
