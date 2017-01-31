package xyz.upperlevel.opencraft.world.block.defaults;

import org.joml.Vector3f;
import xyz.upperlevel.opencraft.world.Zone3f;
import xyz.upperlevel.opencraft.world.BlockComponent;
import xyz.upperlevel.opencraft.world.BlockType;

public class TestBlockType extends BlockType {

    public TestBlockType() {
        super("test_block");
        // creates main block component
        BlockComponent mainBlock = new BlockComponent(new Zone3f(new Vector3f(0), new Vector3f(.5f)));
        shape.addComponent(mainBlock);

        BlockComponent otherB = new BlockComponent(new Zone3f(new Vector3f(.5f), new Vector3f(.6f)));
    }
}
