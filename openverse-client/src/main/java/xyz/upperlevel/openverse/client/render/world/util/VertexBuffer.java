package xyz.upperlevel.openverse.client.render.world.util;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;

import java.nio.ByteBuffer;

import static xyz.upperlevel.openverse.util.math.MathUtil.roundUp;

public class VertexBuffer {
    public static final int CAPACITY_STEP = 2 * 1024 * 1024;

    private final VertexBufferPool pool;
    private ByteBuffer handle;
    @Getter
    private boolean claimed = false;

    public VertexBuffer(VertexBufferPool pool) {
        this.pool = pool;
    }

    public void ensureCapacity(int capacity) {
        if (handle == null || handle.capacity() < capacity) {
            int finalCap;
            if (capacity > 0)
                finalCap = roundUp(capacity, CAPACITY_STEP);
            else
                finalCap = CAPACITY_STEP;

            // TODO global state for OpenverseClient in order to access Logger?
            //client.getLogger().fine("Resizing buffer from " + (handle == null ? 0 : handle.capacity()) + " to " + capacity + " (" + finalCap + ")");

            handle = BufferUtils.createByteBuffer(finalCap);
        } else {
            handle.clear();
        }
    }

    public ByteBuffer byteBuffer() {
        if (handle == null)
            throw new IllegalStateException("ensureCapacity not yet called!");
        return handle;
    }

    public int capacity() {
        return handle == null ? 0 : handle.capacity();
    }

    public VertexBuffer claim() {
        if (!claimed) {
            claimed = true;
        }
        return this;
    }

    public VertexBuffer release() {
        if (pool != null && claimed) {
            claimed = false;
            pool.release(this);
        }
        return this;
    }
}
