package xyz.upperlevel.opencraft.renderer.test;

import xyz.upperlevel.opencraft.renderer.WorldViewer;
import world.Block;

public class BlockInFrustumTest implements BlockRenderTest {

    public static final BlockRenderTest $ = new BlockInFrustumTest();

    public BlockInFrustumTest() {
    }

    @Override
    public boolean canRender(WorldViewer viewer, Block block) {
        return true;
    }

    public static BlockRenderTest $() {
        return $;
    }
}
