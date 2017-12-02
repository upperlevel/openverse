package xyz.upperlevel.openverse.network.world.registry;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.network.SerialUtil;
import xyz.upperlevel.openverse.world.block.BlockType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
public class BlockRegistryPacket implements Packet {
    @Getter
    private String[] blocks;


    public BlockRegistryPacket(Stream<BlockType> blockTypes) {
        blocks = blockTypes.map(BlockType::getId).toArray(String[]::new);
    }

    public BlockRegistryPacket(Collection<BlockType> blockTypes) {
       this(blockTypes.stream());
    }

    @Override
    public void toData(ByteBuf out) {
        System.out.println("Sending BlockRegistryPacket: " + Arrays.toString(blocks));
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
        System.out.println("Received BlockRegistryPacket: " + Arrays.toString(blocks));
    }
}
