package xyz.upperlevel.openverse.network.world.registry;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.network.SerialUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
public class ItemRegistryPacket implements Packet {
    @Getter
    private String[] items;


    public ItemRegistryPacket(Stream<ItemType> itemTypes) {
        items = itemTypes.map(ItemType::getId).toArray(String[]::new);
    }

    public ItemRegistryPacket(Collection<ItemType> itemTypes) {
        this(itemTypes.stream());
    }

    @Override
    public void toData(ByteBuf out) {
        System.out.println("Sending ItemRegistryPacket: " + Arrays.toString(items));
        out.writeInt(items.length);
        for (String str : items) {
            SerialUtil.writeString(str, out);
        }
    }

    @Override
    public void fromData(ByteBuf in) {
        int len = in.readInt();
        items = new String[len];
        for (int i = 0; i < len; i++) {
            items[i] = SerialUtil.readString(in);
        }
        System.out.println("Received ItemRegistryPacket: " + Arrays.toString(items));
    }
}
