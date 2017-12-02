package xyz.upperlevel.openverse.network;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class SerialUtil {
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final char TERMINATOR = (char)0;

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
}
