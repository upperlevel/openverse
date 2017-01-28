package xyz.upperlevel.opencraft.render.block;

import xyz.upperlevel.opencraft.render.Renderer;
import xyz.upperlevel.opencraft.world.Block;

public interface BlockRenderer extends Renderer {

    void render(Block block);
}
