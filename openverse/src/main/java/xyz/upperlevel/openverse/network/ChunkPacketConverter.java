package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

import java.nio.ByteBuffer;

import static xyz.upperlevel.openverse.network.SerialUtil.*;

public class ChunkPacketConverter implements PacketConverter<ChunkPacket> {

    @Override
    public byte[] toData(ChunkPacket packet) {
        ByteBuffer buffer = ByteBuffer.allocate(
                        packet.getWorld().length() + 1 +    //World name
                        3 * Integer.BYTES +                 //Location
                        getBlocksSize(packet)               //Blocks
        );

        //Name
        writeString(packet.getWorld(), buffer);

        //Location
        writeChunkLocation(packet.getLocation(), buffer);

        //Blocks
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    final String id = packet.getBlockType(x, y, z);
                    writeString(id != null ? id : "", buffer);
                }
            }
        }

        //Serialize
        buffer.flip();
        byte[] res = new byte[buffer.remaining()];
        buffer.get(res);
        return res;
    }

    @Override
    public ChunkPacket toPacket(byte[] data) {
        String name;
        ChunkLocation location;
        String[][][] blocks = new String[16][16][16];

        ByteBuffer in = ByteBuffer.wrap(data);

        name = readString(in);
        location = readChunkLocation(in);

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    final String id = readString(in);
                    if(!id.isEmpty())
                        blocks[x][y][z] = id;
                }
            }
        }
        return new ChunkPacket(name, location, blocks);
    }

    private int getBlocksSize(ChunkPacket packet) {
        int i = 0;
        for (int x = 0; x < 16; x++)
            for (int y = 0; y < 16; y++)
                for (int z = 0; z < 16; z++)
                    i += packet.getBlockType(x, y, z).length() + 1;
        return i;
    }

}
