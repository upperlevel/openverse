package xyz.upperlevel.opencraft.render.test;

import xyz.upperlevel.opencraft.render.WorldViewer;
import xyz.upperlevel.opencraft.world.Block;

public interface BlockRenderTest {

    boolean canRender(WorldViewer viewer, Block block);
}
