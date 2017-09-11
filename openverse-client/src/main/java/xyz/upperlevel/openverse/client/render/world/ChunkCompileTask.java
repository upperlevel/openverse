package xyz.upperlevel.openverse.client.render.world;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.world.util.VertexBuffer;
import xyz.upperlevel.openverse.client.render.world.util.VertexBufferPool;

import java.util.Queue;
import java.util.logging.Level;

@RequiredArgsConstructor
public class ChunkCompileTask implements Runnable {
    private final VertexBufferPool bufferPool;
    private final ChunkRenderer chunk;
    private final Queue<ChunkUploader> uploaders;

    @Override
    public void run() {
        if (!chunk.isAlive()) {
            return;
        }
        VertexBuffer buffer;
        try {
            buffer = bufferPool.waitForBuffer();
        } catch (InterruptedException e) {
            Openverse.logger().log(Level.WARNING, "Chunk compiler: interrupted pool retrieving");
            return;
        }
        buffer.ensureCapacity(chunk.getAllocateDataCount() * Float.BYTES);
        int count;
        try {
            count = chunk.compile(buffer.byteBuffer());
        } catch (Exception e) {
            Openverse.logger().log(Level.SEVERE, "Error while compiling chunk (data:" + (chunk.getAllocateDataCount() * Float.BYTES) + ", cap:" + buffer.byteBuffer().capacity() + ")", e);
            bufferPool.release(buffer);
            return;
        }
        uploaders.add(new ChunkUploader(buffer, chunk, count));
    }
}
