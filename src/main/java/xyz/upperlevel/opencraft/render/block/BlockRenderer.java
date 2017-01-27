package xyz.upperlevel.opencraft.render;

import xyz.upperlevel.opencraft.world.Block;

public interface BlockRenderer extends Renderer {

    void render(Block block);
}
