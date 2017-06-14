package xyz.upperlevel.openverse.network;

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

    public static String readString(ByteBuffer buf) {
        StringBuilder out = new StringBuilder();
        char c;
        while ((c = (char) buf.get()) != TERMINATOR)
            out.append(c);
        return out.toString();
    }

    public static void writeString(String str, ByteBuffer out) {
        out     .put(str.getBytes(CHARSET))
                .put((byte) TERMINATOR);
    }


    public static ChunkLocation readChunkLocation(ByteBuffer in) {
        return new ChunkLocation(//And finally get the location
                in.getInt(),
                in.getInt(),
                in.getInt()
        );
    }
    public static void writeChunkLocation(ChunkLocation loc, ByteBuffer out) {
        out.putInt(loc.x);
        out.putInt(loc.y);
        out.putInt(loc.z);
    }
}
