package xyz.upperlevel.opencraft.render.chunk;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
public class ChunkRendererSupplier {

    public static final ChunkRendererSupplier $ = new ChunkRendererSupplier();

    @Getter
    @Setter
    @NonNull
    private ChunkRenderer renderer = SimpleChunkRenderer.$;

    public static ChunkRendererSupplier $() {
        return $;
    }
}