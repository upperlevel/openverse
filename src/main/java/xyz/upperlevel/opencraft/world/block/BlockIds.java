package xyz.upperlevel.opencraft.world.block;

import xyz.upperlevel.opencraft.world.block.defaults.TestBlockId;

public final class BlockIds {

    private BlockIds() {
    }

    public static final BlockId NULL_BLOCK = new BlockId("_null") {
        private final BlockState NULL = new BlockState(this);

        @Override
        public BlockState generateState() {
            return NULL;
        }
    };

    public static final TestBlockId TEST_BLOCK = new TestBlockId();
}
