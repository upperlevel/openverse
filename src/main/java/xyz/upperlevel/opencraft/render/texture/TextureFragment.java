package xyz.upperlevel.opencraft.render.texture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextureFragment {

    @Getter
    public final int id;

    @Getter
    public final TextureHub hub;

    @Getter
    public final int width, height;

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
}
