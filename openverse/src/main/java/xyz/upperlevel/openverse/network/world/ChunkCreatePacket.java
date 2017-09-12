package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockSystem;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

import javax.naming.OperationNotSupportedException;

import static xyz.upperlevel.openverse.network.SerialUtil.*;

@Getter
@NoArgsConstructor
public class ChunkCreatePacket implements Packet {
    private ChunkLocation location;
    private int[][][] blockTypes;

    public ChunkCreatePacket(Chunk chunk) {
        this.location = chunk.getLocation();
        this.blockTypes = new int[16][16][16];
        for (int ix = 0; ix < 16; ix++) {
            for (int iy = 0; iy < 16; iy++) {
                for (int iz = 0; iz < 16; iz++) {
                    BlockType ty = chunk.getBlockType(ix, iy, iz);
                    if (ty != null) {
                        this.blockTypes[ix][iy][iz] = ty.getRawId();
                    }
                }
            }
        }
    }

    public int getBlockType(int x, int y, int z) {
        return blockTypes[x][y][z];
    }

    public Chunk getChunk(World world) {
        Chunk chk = new Chunk(world, location);
        for (int x = 0; x < Chunk.WIDTH; x++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (int z = 0; z < Chunk.LENGTH; z++) {
                    if (blockTypes[x][y][z] != 0) {
                        BlockType ty = Openverse.resources().blockTypes().entry(blockTypes[x][y][z]);
                        if (ty == null) {
                            Openverse.logger().warning("Unsolved id in ChunkCreatePacket: " + blockTypes[x][y][z]);
                        }
                        chk.setBlockType(x, y, z, ty);
                    }
                }
            }
        }
        return chk;
    }

    @Override
    public void toData(ByteBuf out) {
        //Location
        writeChunkLocation(location, out);

        //Blocks
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    out.writeInt(getBlockType(x, y, z));
                }
            }
        }
    }

    @Override
    public void fromData(ByteBuf in) {
        location = readChunkLocation(in);
        blockTypes = new int[16][16][16];

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    blockTypes[x][y][z] = in.readInt();
                }
            }
        }
    }
}
