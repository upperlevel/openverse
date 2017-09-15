package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.network.SerialUtil;
import xyz.upperlevel.openverse.world.block.BlockType;

import java.util.Arrays;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
public class BlockRegistryPacket implements Packet {
    @Getter
    private String[] blocks;

    public BlockRegistryPacket(Collection<BlockType> blockTypes) {
        blocks = blockTypes.stream().map(BlockType::getId).toArray(String[]::new);
    }

    @Override
    public void toData(ByteBuf out) {
        System.out.println("Sending BlockRegistryPacket: " + Arrays.asList(blocks));
        out.writeInt(blocks.length);
        for (String str : blocks) {
            SerialUtil.writeString(str, out);
        }
    }

    @Override
    public void fromData(ByteBuf in) {
        int len = in.readInt();
        blocks = new String[len];
        for (int i = 0; i < len; i++) {
            blocks[i] = SerialUtil.readString(in);
        }
        System.out.println("Received BlockRegistryPacket: " + Arrays.asList(blocks));
    }
}
