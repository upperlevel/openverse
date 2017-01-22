package xyz.upperlevel.opencraft.world.block.defaults;

import xyz.upperlevel.graphicengine.api.util.Color;
import xyz.upperlevel.opencraft.world.block.BlockFacePosition;
import xyz.upperlevel.opencraft.world.block.BlockId;
import xyz.upperlevel.opencraft.world.block.BlockState;

public class TestBlockState extends BlockState {

    public TestBlockState(BlockId id) {
        super(id);
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
