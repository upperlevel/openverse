package xyz.upperlevel.opencraft.world.block.id;

import xyz.upperlevel.opencraft.world.block.shape.BlockShape;
import xyz.upperlevel.opencraft.world.block.state.TestBlockState;

public class TestBlockId extends BlockId {

    public static final TestBlockId $ = new TestBlockId(); // todo

    public TestBlockId(BlockShape shape) {
        super("test_block", shape);
    }

    @Override
    public TestBlockState generateState() {
        return new TestBlockState();
    }

    public static TestBlockId $() {
        return $;
    }
}
