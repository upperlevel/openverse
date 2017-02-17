package world;

import world.block.defaults.TestBlockType;

public final class BlockTypes {

    private BlockTypes() {
    }

    public static final BlockType NULL_BLOCK = new BlockType("_null") {
        {
            getShape()
                    .setBulky(true)
                    .setTransparent(true);
        }
    };

    public static final TestBlockType TEST_BLOCK = new TestBlockType();
}
