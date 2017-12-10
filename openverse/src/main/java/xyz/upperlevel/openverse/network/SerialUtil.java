package xyz.upperlevel.openverse.network;

import io.netty.buffer.ByteBuf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class SerialUtil {
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final char TERMINATOR = (char)0;

    public static String readString(ByteBuf in) {
        StringBuilder out = new StringBuilder();
        char c;
        while ((c = (char) in.readByte()) != TERMINATOR) {
            out.append(c);
        }
        return out.toString();
    }

    public static String readString(DataInput in) throws IOException {
        StringBuilder out = new StringBuilder();
        char c;
        while ((c = (char) in.readByte()) != TERMINATOR) {
            out.append(c);
        }
        return out.toString();
    }

    public static void writeString(String str, ByteBuf out) {
        out     .writeBytes(str.getBytes(CHARSET))
                .writeByte(TERMINATOR);
    }

    public static void writeString(String str, DataOutput out) throws IOException {
        out.write(str.getBytes(CHARSET));
        out.writeByte(TERMINATOR);
    }

    public static int unsignNumber(Number n) {
        int i = n.intValue();
        if (n.getClass() == Byte.class) {
            return i & 0xFF;
        } else if (n.getClass() == Short.class) {
            return i & 0xFFFF;
        } else return i;
    }
}
