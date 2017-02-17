package xyz.upperlevel.opencraft.renderer.test;

import xyz.upperlevel.opencraft.renderer.WorldViewer;
import world.Block;

public interface BlockRenderTest {

    boolean canRender(WorldViewer viewer, Block block);
}
