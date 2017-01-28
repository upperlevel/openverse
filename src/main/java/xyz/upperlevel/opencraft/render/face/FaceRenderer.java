package xyz.upperlevel.opencraft.render.face;

import xyz.upperlevel.opencraft.render.Renderer;
import xyz.upperlevel.opencraft.world.block.BlockFace;

public interface FaceRenderer extends Renderer {

    void render(BlockFace face);
}