package xyz.upperlevel.opencraft.render;

import xyz.upperlevel.opencraft.world.Chunk;

public interface ChunkRenderer extends Renderer {

    void render(Chunk chunk);
}