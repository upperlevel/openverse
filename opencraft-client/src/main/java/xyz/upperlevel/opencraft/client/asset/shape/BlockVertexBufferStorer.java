package xyz.upperlevel.opencraft.client.block;

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

    public static void putPosition(ByteBuffer buffer, float x, float y, float z) {
        putFloats(buffer, x, y, z);
    }

    public static void putPosition(ByteBuffer buffer, Vector3f position) {
        putPosition(buffer, position.x, position.y, position.z);
    }

    public static  void putColor(ByteBuffer buffer, float r, float g, float b, float a) {
        putFloats(buffer, r, g, b, a);
    }

    public static void putColor(ByteBuffer buffer, Color color) {
        putColor(buffer, color.r, color.g, color.b, color.a);
    }

    public static void putTextureCoordinates(ByteBuffer buffer, float u, float v) {
        putFloats(buffer, u, v);
    }

    public static void putTextureCoordinates(ByteBuffer buffer, Vector2f textureCoords) {
        putTextureCoordinates(buffer, textureCoords.x, textureCoords.y);
    }
}