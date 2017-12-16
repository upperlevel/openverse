package xyz.upperlevel.openverse.item;

import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.property.BlockProperty;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class BlockItemType extends ItemType {
    @Getter
    private final BlockType blockType;

    public BlockItemType(BlockType blockType) {
        super(blockType.getId());
        this.blockType = blockType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ItemStack getStackWithData(int count, Map<String, Object> inData) {
        ItemStack itemStack = new ItemStack(this, count);

        BlockStateRegistry registry = blockType.getStateRegistry();
        BlockState state = blockType.getDefaultState();
        for (BlockProperty<?> property : registry.getProperties()) {
            if (!inData.containsKey(property.getName())) {
                continue;
            }
            Optional<?> parsed = property.parse(inData.get(property.getName()).toString());
            if (parsed.isPresent()) {
                state = state.with((BlockProperty)property, (Comparable) parsed.get());
            }
        }
        itemStack.setState((byte) state.getId());

        return itemStack;
    }

    @Override
    public byte getDefaultState() {
        return (byte) blockType.getDefaultState().getId();
    }

    @Override
    public IntStream getStates() {
        return IntStream.range(0, blockType.getStateRegistry().getStates().size());
    }

    @Override
    public boolean onUseBlock(Player player, ItemStack itemStack, int x, int y, int z, BlockFace face) {
        switch (face) {
            case UP:    y += 1; break;
            case DOWN:  y -= 1; break;
            case RIGHT: x += 1; break;
            case LEFT:  x -= 1; break;
            case BACK:  z += 1; break;
            case FRONT: z -= 1; break;
        }
        World world = player.getWorld();
        world.setBlockState(x, y, z, blockType.getStateWhenPlaced(player, itemStack, x, y, z));
        return true;
    }
}
