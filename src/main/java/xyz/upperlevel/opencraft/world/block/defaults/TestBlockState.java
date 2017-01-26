package xyz.upperlevel.opencraft.world.block.defaults;

import xyz.upperlevel.opencraft.world.block.BlockShape;
import xyz.upperlevel.ulge.util.Color;
import xyz.upperlevel.opencraft.world.block.BlockFacePosition;
import xyz.upperlevel.opencraft.world.block.BlockId;
import xyz.upperlevel.opencraft.world.block.BlockState;

public class TestBlockState extends BlockState {

    public TestBlockState(BlockId id) {
        super(id);
    }

    public TestBlockState(BlockId id, BlockShape shape) {
        super(id, shape);
    }

    public void setColor(Color color) {
        getShape().getComponent("main_block").ifPresent(component -> {
            // sets color to all faces of the main_block
            for (BlockFacePosition pos : BlockFacePosition.values()) {
                TestFaceData fd = new TestFaceData();
                fd.setColor(color);
                component.getFace(pos).setData(fd);
            }
        });
    }
}
