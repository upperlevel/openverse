package xyz.upperlevel.opencraft.render.test;

import xyz.upperlevel.opencraft.render.WorldViewer;
import xyz.upperlevel.opencraft.world.Block;

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
