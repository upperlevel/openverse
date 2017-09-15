package xyz.upperlevel.openverse.client.render.world.util;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class VertexBufferPool {
    private final int limit;
    private BlockingQueue<VertexBuffer> buffers;
    private AtomicInteger size = new AtomicInteger(0);

    public VertexBufferPool(int limit) {
        this.limit = limit;
        this.buffers = new PriorityBlockingQueue<>(limit, new ByteBufCapacityComparator());
    }

    /**
     * Tries to get a vertex buffer instantly or, if the limit has reached and the others aren't available, returns null
     * @return a usable VertexBuffer or null if none is available
     */
    public VertexBuffer getBufferOrNull() {
        VertexBuffer buf = buffers.poll();
        if (buf == null) {
            if (tryIncrementSize()) {
                return null;
            } else {
                return new VertexBuffer(this);
            }
        } else {
            return buf;
        }
    }

    /**
     * Gets the biggest VertexBuffer available, if none is available it waits until one is released
     * @return a usable VertexVuffer
     * @throws InterruptedException if the program is interrupted while waiting
     */
    public VertexBuffer waitForBuffer() throws InterruptedException {
        VertexBuffer buf = buffers.poll();
        if (buf == null) {
            if (tryIncrementSize()) {
                return buffers.take();
            } else {
                return new VertexBuffer(this);
            }
        } else {
            return buf;
        }
    }

    public void release(VertexBuffer buf) {
        buffers.add(buf);
    }

    protected boolean tryIncrementSize() {
        return size.updateAndGet(x -> x > limit ? x : (x + 1)) > limit;
    }

    public int size() {
        return size.get();
    }

    public static class ByteBufCapacityComparator implements Comparator<VertexBuffer> {
        @Override
        public int compare(VertexBuffer a, VertexBuffer b) {
            //The capacity is inverted so that the buffer with the biggest capacity goes first while the one with the
            //Lowest goes last
            return Integer.compare(b.capacity(), a.capacity());
        }
    }
}
