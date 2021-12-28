package xyz.upperlevel.openverse.item;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;

import java.util.HashMap;
import java.util.Map;

public class ItemStack {
    public static final ItemStack EMPTY = new ItemStack(ItemType.AIR, 0);
    public static final int ITEMS_COUNT_PER_STACK = 64;

    @Getter
    private int typeRawId;

    @Getter
    @Setter
    private int count;

    @Getter
    @Setter
    private int state;

    @Getter
    private Map<String, Object> data = new HashMap<>();

    // TODO do we really need all of these constructors?

    public ItemStack(int typeRawId, int count, int state) {
        this.typeRawId = typeRawId;
        this.count     = count;
        this.state     = state;
    }

    public ItemStack(ItemType type, int count, int state) {
        this(type.getRawId(), count, state);
    }

    public ItemStack(ItemType type, int count) {
        this(type, count, type.getDefaultState());
    }

    public ItemStack(ItemType type) {
        this(type, 1, type.getDefaultState());
    }

    public ItemType getType(ItemTypeRegistry itemTypeRegistry) {
        return itemTypeRegistry.entry(typeRawId);
    }

    public boolean isEmpty() {
        return count == 0 || typeRawId == ItemType.AIR.getRawId();
    }

    public int addCount(int n) {
        int newCount = Math.min(n, ITEMS_COUNT_PER_STACK); // TODO it would be cool to have max stack count depending on ItemType
        int remaining = (newCount - count) - n;
        count = newCount;
        return remaining;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return  true;
        if (!(other instanceof ItemStack)) return false;
        ItemStack o = (ItemStack) other;
        return o.typeRawId == typeRawId && o.count == count;
    }

    public ItemStack copy() {
        return new ItemStack(typeRawId, count, state);
    }

    public static ItemStack fromData(ByteBuf in) {
        return new ItemStack(in.readInt(), in.readInt(), in.readByte());
    }

    public void toData(ByteBuf out) {
        out.writeInt(typeRawId);
        out.writeInt(count);
        out.writeByte(state);
        //TODO Write obf data
    }

    @Override
    public String toString() {
        return typeRawId + (count != 0 ? "[" + count + "]" : "");
    }
}
