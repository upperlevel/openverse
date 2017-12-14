package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
@NoArgsConstructor
public class ChunkCreatePacket implements Packet {
    private int x, y, z;
    private int[] states = new int[16 * 16 * 16];

    public ChunkCreatePacket(Chunk chunk) {
        this.x = chunk.getX();
        this.y = chunk.getY();
        this.z = chunk.getZ();
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockState bType = chunk.getBlockState(x, y, z);
                    if (bType != null && bType != BlockType.AIR) {
                        states[x << 8 | y << 4 | z] = bType.getFullId();
                    }
                }
            }
        }
    }

    public void setBlockStates(Chunk chunk) {
        BlockTypeRegistry reg = Openverse.resources().blockTypes();
        for (int i = 0; i < 16 * 16 * 16; i++) {
            if (states[i] != 0) {
                BlockState state = reg.getState(states[i]);
                if (state == null) {
                    Openverse.getLogger().warning("Unresolved block-state id in ChunkCreatePacket: " + states[i]);
                } else {
                    chunk.setBlockState(i >> 8, i >> 4 & 0xF, i & 0xF, state, false);
                }
            }
        }
    }

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        for (int i = 0; i < 16 * 16 * 16; i++) {
            out.writeInt(states[i]);
        }
    }

    @Override
    public void fromData(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        for (int i = 0; i < states.length; i++) {
            states[i] = in.readInt();
        }
    }
}
