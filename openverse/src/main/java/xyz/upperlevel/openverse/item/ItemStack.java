package xyz.upperlevel.openverse.item;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;

import java.util.HashMap;
import java.util.Map;

public class ItemStack {
    public static final ItemStack EMPTY = new ItemStack(ItemType.AIR, 0);

    @Getter
    private final ItemType type;
    @Getter
    @Setter
    private int count;
    @Getter
    @Setter
    private int state;
    @Getter
    private Map<String, Object> data = new HashMap<>();

    public ItemStack(ItemType type, int count, byte state) {
        this.type = type;
        this.count = count;
        this.state = state;
    }

    public ItemStack(ItemType type, int count) {
        this(type, count, type.getDefaultState());
    }

    public ItemStack(ItemType type) {
        this(type, 1, type.getDefaultState());
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
        if (type.getRawId() < 0) {
            throw new IllegalStateException("Item not yet registered: " + type.getId());
        }
        out.writeInt(type.getRawId());
        out.writeInt(count);
        out.writeByte(state);
        //TODO Write obf data
    }

    public static ItemStack fromData(ByteBuf in) {
        int itemTypeId = in.readInt();
        ItemType itemType = Openverse.resources().itemTypes().entry(itemTypeId);
        if (itemType == null) {
            throw new IllegalArgumentException("Invalid data: cannot find typeId " + itemTypeId);
        }
        //TODO Read obf data
        return new ItemStack(itemType, in.readInt(), in.readByte());
    }

    @Override
    public String toString() {
        return type.getId() + (count != 0 ? "[" + count + "]" : "");
    }
}
