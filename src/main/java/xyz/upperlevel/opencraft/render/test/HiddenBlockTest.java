package xyz.upperlevel.opencraft.render.test;

import xyz.upperlevel.opencraft.render.WorldViewer;
import xyz.upperlevel.opencraft.world.Block;
import xyz.upperlevel.opencraft.world.Relative;

public class HiddenBlockTest implements BlockRenderTest {

    public static final BlockRenderTest $ = new BlockInFrustumTest();

    public HiddenBlockTest() {
    }

    @Override
    public boolean canRender(WorldViewer viewer, Block block) {
        for (Relative relative : Relative.values())
            if (block.getRelative(relative).getId().isTransparent())
                return true;
        return false;
    }

    public static BlockRenderTest $() {
        return $;
    }
}
