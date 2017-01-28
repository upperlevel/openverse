package xyz.upperlevel.opencraft.render.chunk;

import xyz.upperlevel.opencraft.world.Chunk;

public class SimpleChunkRenderer implements ChunkRenderer{

    public static final ChunkRenderer $ = new SimpleChunkRenderer();

    @Override
    public void render(Chunk chunk) {
        // todo
    }

    public static ChunkRenderer $() {
        return $;
    }
}