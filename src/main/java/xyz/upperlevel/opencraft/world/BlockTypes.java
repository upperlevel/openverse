package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.world.block.defaults.TestBlockType;

public final class BlockTypes {

    private BlockTypes() {
    }

    public static final BlockType NULL_BLOCK = new BlockType("_null") {
        {
            empty = true;
            transparent = true;
        }
    };

    public static final TestBlockType TEST_BLOCK = new TestBlockType();
}
