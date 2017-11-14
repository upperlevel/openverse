package xyz.upperlevel.openverse.item;

import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.entity.player.Player;

public class BlockItemType extends ItemType {
    @Getter
    private final BlockType blockType;

    public BlockItemType(BlockType blockType) {
        super(blockType.getId());
        this.blockType = blockType;
    }

    @Override
    public boolean onUseBlock(Player player, int x, int y, int z, BlockFace face) {
        switch (face) {
            case UP:    y += 1; break;
            case DOWN:  y -= 1; break;
            case RIGHT: x += 1; break;
            case LEFT:  x -= 1; break;
            case BACK:  z += 1; break;
            case FRONT: z -= 1; break;
        }
        World world = player.getWorld();
        world.setBlockState(x, y, z, blockType.getStateWhenPlaced(player, x, y, z));
        return true;
    }
}
