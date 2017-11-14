package xyz.upperlevel.openverse.item;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;

public class ItemStack {
    public static final ItemStack EMPTY = new ItemStack(ItemType.AIR, 0);

    @Getter
    private final ItemType type;
    @Getter
    @Setter
    private int count;

    public ItemStack(ItemType type, int count) {
        this.type = type;
        this.count = count;
    }

    public ItemStack(ItemType type) {
        this(type, 1);
    }

    public boolean isEmpty() {
        return count == 0 || type == ItemType.AIR;
    }

    public int addCount(int n) {
        int newCount = Math.min(n, type.getMaxStack());
        int remaining = (newCount - count) - n;
        count = newCount;
        return remaining;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return  true;
        if (!(other instanceof ItemStack)) return false;
        ItemStack o = (ItemStack) other;
        return o.type == type && o.count == count;
    }

    public ItemStack copy() {
        return new ItemStack(type, count);
    }

    public void toData(ByteBuf out) {
        out.writeInt(type.getRawId());
        out.writeInt(count);
    }

    public static ItemStack fromData(ByteBuf in) {
        int itemTypeId = in.readInt();
        ItemType itemType = Openverse.resources().itemTypes().entry(itemTypeId);
        if (itemType == null) {
            throw new IllegalArgumentException("Invalid data: cannot find typeId " + itemTypeId);
        }
        return new ItemStack(itemType, in.readInt());
    }
}
