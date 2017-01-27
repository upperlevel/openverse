package xyz.upperlevel.opencraft.render.block;

import java.util.Objects;

public final class BlockRendererSelector {

    private static BlockRenderer renderer; // todo = default one

    private BlockRendererSelector() {
    }

    public static BlockRenderer selected() {
        return renderer;
    }

    public static void select(BlockRenderer renderer) {
        Objects.requireNonNull(renderer, "block renderer cannot be null");
        BlockRendererSelector.renderer = renderer;
    }
}