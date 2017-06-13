package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;
import xyz.upperlevel.openverse.resource.BlockType;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ChunkPacketConverter implements PacketConverter<ChunkPacket> {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @Override
    public byte[] toData(ChunkPacket packet) {
        ByteBuffer buffer = ByteBuffer.allocate(
                packet.getWorld().length() + 1 +         //World name
                3*Integer.BYTES +                        //Location
                (16*16*16)*(BlockType.MAX_ID_LENGTH + 1) //Blocks
        );

        {//Name
            buffer.put(packet.getWorld().getBytes(CHARSET));
            buffer.put((byte) '\0');
        }

        {//Location
            final ChunkLocation loc = packet.getLocation();
            buffer.putInt(loc.x);
            buffer.putInt(loc.y);
            buffer.putInt(loc.z);
        }

        {//Blocks
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        // put all three coordinates in int
                        //short id = x << 16 | y << 8 | z;
                        final String id = packet.getBlockType(x, y, z);
                        if (id != null)
                            buffer.put(id.getBytes(CHARSET));
                        buffer.put((byte) 0);
                    }
                }
            }
        }
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

        IntBuffer index = IntBuffer.wrap(new int[]{0});

        name = getString(data, index);
        location = getLoc(data, index);

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    final String id = getString(data, index);
                    if(!id.isEmpty())
                        blocks[x][y][z] = id;
                }
            }
        }
        return new ChunkPacket(name, location, blocks);
    }

    private static String getString(byte[] data, IntBuffer index) {
        final int start = index.get();
        int i = start;
        while(data[i++] != '\0');
        index.put(i);
        final int end = i - 1;
        return new String(data, start, (end - start), CHARSET);
    }

    private static ChunkLocation getLoc(byte[] data, IntBuffer index) {
        final ByteBuffer in = ByteBuffer.wrap(data, index.get(), 3*Integer.BYTES);//Wtap ByteBuffer

        index.put(in.position());//Update index

        return new ChunkLocation(//And finally get the location
                in.getInt(),
                in.getInt(),
                in.getInt()
        );
    }
}
