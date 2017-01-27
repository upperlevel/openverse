package xyz.upperlevel.opencraft.render.chunk;

import java.util.Objects;

public final class ChunkRendererSelector {

    private static ChunkRenderer renderer; // todo = default one

    private ChunkRendererSelector() {
    }

    public static ChunkRenderer selected() {
        return renderer;
    }

    public static void select(ChunkRenderer renderer) {
        Objects.requireNonNull(renderer, "block renderer cannot be null");
        ChunkRendererSelector.renderer = renderer;
    }
}