package xyz.upperlevel.opencraft.render.chunk;

import xyz.upperlevel.opencraft.render.Renderer;
import xyz.upperlevel.opencraft.world.Chunk;

public interface ChunkRenderer extends Renderer {

    void render(Chunk chunk);
}