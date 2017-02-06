package xyz.upperlevel.opencraft.render.texture;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TextureFragment {

    @Getter
    private final int id;

    @Getter
    private final TextureManager manager;

    @Getter
    private final int width, height;

    @Getter
    float realWidth, realHeight;

    @Getter
    float minU, minV, maxU, maxV;

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
        return
                "{minU: " + minU + " " + " minV: " + minV + " maxU: " + maxU + " maxV: " + maxV + "}";
    }
}
