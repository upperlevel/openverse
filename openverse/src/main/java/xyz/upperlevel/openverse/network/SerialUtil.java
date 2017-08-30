package xyz.upperlevel.openverse.network;

import io.netty.buffer.ByteBuf;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class SerialUtil {
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final char TERMINATOR = (char)0;

    // gets world names total byte size
    public static int getSize(List<String> wn) {
        int i = 0;
        for (String n : wn)
            i += n.length();
        return i;
    }

    public static String readString(ByteBuf in) {
        StringBuilder out = new StringBuilder();
        char c;
        while ((c = (char) in.readByte()) != TERMINATOR)
            out.append(c);
        return out.toString();
    }

    public static void writeString(String str, ByteBuf out) {
        out     .writeBytes(str.getBytes(CHARSET))
                .writeByte(TERMINATOR);
    }


    public static ChunkLocation readChunkLocation(ByteBuf in) {
        return new ChunkLocation(//And finally get the location
                in.readInt(),
                in.readInt(),
                in.readInt()
        );
    }
    public static void writeChunkLocation(ChunkLocation loc, ByteBuf out) {
        out.writeInt(loc.x);
        out.writeInt(loc.y);
        out.writeInt(loc.z);
    }
}
