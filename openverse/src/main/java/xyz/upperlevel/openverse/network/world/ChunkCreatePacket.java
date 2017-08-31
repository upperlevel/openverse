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

import static xyz.upperlevel.openverse.network.SerialUtil.*;

@Getter
@NoArgsConstructor
public class ChunkCreatePacket implements Packet {
    private ChunkLocation location;
    private String[][][] blockTypes;

    public ChunkCreatePacket(Chunk chunk) {
        this.location = chunk.getLocation();
        this.blockTypes = new String[16][16][16];
        for (int ix = 0; ix < 16; ix++) {
            for (int iy = 0; iy < 16; iy++) {
                for (int iz = 0; iz < 16; iz++) {
                    BlockType ty = chunk.getBlock(ix, iy, iz).getType();
                    if (ty != null)
                        this.blockTypes[ix][iy][iz] = ty.getId();
                }
            }
        }
    }

    public String getBlockType(int x, int y, int z) {
        return blockTypes[x][y][z];
    }

    public Chunk getChunk(World world) {
        Chunk chk = new Chunk(world, location);
        for (int x = 0; x < Chunk.WIDTH; x++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (int z = 0; z < Chunk.LENGTH; z++) {
                    if (blockTypes[x][y][z] != null) {
                        BlockType ty = Openverse.resources().blockTypes().entry(blockTypes[x][y][z]);
                        chk.getBlock(x, y, z).setType(ty);
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
                    String id = getBlockType(x, y, z);
                    writeString(id != null ? id : "", out);
                }
            }
        }
    }

    @Override
    public void fromData(ByteBuf in) {
        location = readChunkLocation(in);
        blockTypes = new String[16][16][16];

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    String id = readString(in);
                    if (!id.isEmpty()) {
                        blockTypes[x][y][z] = id;
                    }
                }
            }
        }
    }
}
