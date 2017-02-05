package xyz.upperlevel.opencraft.render.texture;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class TextureIndexer {

    @Getter
    @NonNull
    private final TextureFragment fragment;

    @Getter
    @Setter
    private float u, v;

    public float getMinU() {
        return fragment.getMinU();
    }

    public float getMaxU() {
        return fragment.getMinU() + u * fragment.getRealWidth();
    }

    public float getMinV() {
        return fragment.getMinV();
    }

    public float getMaxV() {
        return v;
    }
}
