package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class VertexBuffer {

    @Getter
    public final int size;

    @Getter
    public final FloatBuffer data;

    public VertexBuffer(int size) {
        this.size = size;
        data = BufferUtils.createFloatBuffer(size);
    }

    public VertexBuffer position(float x, float y, float z) {
        data.put(x);
        data.put(y);
        data.put(z);
        return this;
    }

    public VertexBuffer position(Vector3f position) {
        position(position.x, position.y, position.z);
        return this;
    }

    public VertexBuffer position(float x, float y, float z, float w) {
        data.put(x);
        data.put(y);
        data.put(z);
        data.put(w);
        return this;
    }

    public VertexBuffer position(Vector4f position) {
        position(position.x, position.y, position.z, position.w);
        return this;
    }

    public VertexBuffer color(float r, float g, float b, float a) {
        data.put(r);
        data.put(b);
        data.put(g);
        data.put(a);
        return this;
    }

    public VertexBuffer color(Color color) {
        data.put(color.r);
        data.put(color.g);
        data.put(color.b);
        data.put(color.a);
        return this;
    }

    public VertexBuffer texture(float u, float v) {
        data.put(u);
        data.put(v);
        return this;
    }
}
