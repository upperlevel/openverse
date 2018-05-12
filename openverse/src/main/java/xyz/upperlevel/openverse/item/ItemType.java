package xyz.upperlevel.openverse.item;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.BitSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

@Getter
public class ItemType {
    public static final ItemType AIR = new ItemType("air");

    private final String id;

    @Setter
    private int rawId = -1;

    public ItemType(String id) {
        this.id = id;
    }

    /**
     * Called when the player uses (right-clicks) a block with the item
     * @param player the player who clicked the block
     * @param itemStack the ItemStack with which the block was clicked
     * @param x the block's x location
     * @param y the block's y location
     * @param z the block's z location
     * @param face the clicked block face
     * @return true only if the item has been successfully used
     */
    public boolean onUseBlock(Player player, ItemStack itemStack, int x, int y, int z, BlockFace face) {
        return false;
    }

    public ItemStack getStackWithData(int count, Map<String, Object> inData) {
        ItemStack itemStack = new ItemStack(this, count);
        itemStack.getData().putAll(inData);
        return itemStack;
    }

    /**
     * Returns the max stack size that this item can have
     * @return the max stack size
     */
    public int getMaxStack() {
        return 64;
    }

    public byte getDefaultState() {
        return 0;
    }

    public IntStream getStates() {
        return IntStream.of(0);
    }

    /**
     * Creates a new Item {@link BlockItemType} for the {@link BlockType} passed as argument (with the default implementation)
     * @param type the block type used to generate the item type
     * @return a new {@link BlockItemType} generated from the block type passed as argument
     */
    public static BlockItemType createFromBlock(BlockType type) {
        return new BlockItemType(type);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof ItemType)) return false;
        return ((ItemType) other).getId().equals(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
