package world;

import org.joml.Vector2f;
import org.joml.Vector3f;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

public final class BlockVertexBufferStorer {

    private BlockVertexBufferStorer() {
    }

    private static void putFloats(ByteBuffer buffer, float... values) {
        for (float value : values)
            buffer.putFloat(value);
    }

    public static void setPosition(ByteBuffer buffer, float x, float y, float z) {
        putFloats(buffer, x, y, z);
    }

    public static void setPosition(ByteBuffer buffer, Vector3f position) {
        setPosition(buffer, position.x, position.y, position.z);
    }

    public static  void setColor(ByteBuffer buffer, float r, float g, float b, float a) {
        putFloats(buffer, r, g, b, a);
    }

    public static void setColor(ByteBuffer buffer, Color color) {
        setColor(buffer, color.r, color.g, color.b, color.a);
    }

    public static void setTextureCoords(ByteBuffer buffer, float u, float v) {
        putFloats(buffer, u, v);
    }

    public static void setTextureCoords(ByteBuffer buffer, Vector2f textureCoords) {
        setTextureCoords(buffer, textureCoords.x, textureCoords.y);
    }
}