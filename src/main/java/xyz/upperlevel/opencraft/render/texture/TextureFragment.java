package xyz.upperlevel.opencraft.render.texture;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Texture {

    @Getter
    public final int id;

    @Getter
    public final TextureManager manager;

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
        return object instanceof Texture && ((Texture) object).id == id;
    }

    @Override
    public String toString() {
        return "{w: " + width + " h: " + height
                + " real_w: " + realWidth
                + " real_h: " + realHeight
                + " minU: " + minU
                + " maxU: " + maxU
                + " minV: " + minV
                + " maxV: " + maxV
                + "}";
    }
}
