package xyz.upperlevel.opencraft.world.block.defaults;

import xyz.upperlevel.graphicengine.api.util.Colors;
import xyz.upperlevel.opencraft.world.block.BlockComponent;
import xyz.upperlevel.opencraft.world.block.BlockFacePosition;
import xyz.upperlevel.opencraft.world.block.BlockId;
import xyz.upperlevel.opencraft.world.block.BlockState;

public class TestBlockId extends BlockId {

    public TestBlockId() {
        super("test_block");
        // creates main block component
        BlockComponent mainBlock = new BlockComponent("main_block");
        {
            // sets face data
            TestFaceData faceData = new TestFaceData();
            faceData.setColor(Colors.RED);
            mainBlock.getFace(BlockFacePosition.UP).setData(faceData);
        }
        // updates the shape with the created component
        getShape().addComponent(mainBlock);
    }

    @Override
    public BlockState generateState() {
        return new TestBlockState(this);
    }
}
