package xyz.upperlevel.openverse.client.render.world;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.world.util.VertexBuffer;
import xyz.upperlevel.openverse.client.render.world.util.VertexBufferPool;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

@RequiredArgsConstructor
public class ChunkCompileTask {
    private final VertexBufferPool bufferPool;
    private final ChunkRenderer chunk;
    private ReentrantLock stateLock = new ReentrantLock();
    private VertexBuffer buffer;
    private int vertexCount;
    private State state = State.PENDING;

    protected void askBuffer() {
        if (buffer == null) {
            try {
                buffer = bufferPool.waitForBuffer();
            } catch (InterruptedException e) {
                Openverse.logger().log(Level.WARNING, "Chunk compiler: interrupted pool retrieving");
            }
        }
    }

    public void compile() {
        stateLock.lock();
        try {
            if (state == State.ABORTED) {
                return;
            }
            if (state != State.PENDING) {
                throw new IllegalArgumentException("Cannot compile an already compiled chunk (state: " + state + ")");
            }
            state = State.COMPILING;
        } finally {
            stateLock.unlock();
        }

        askBuffer();
        if (buffer == null) {
            //Buffer retrieving failed
            return;
        }

        buffer.ensureCapacity(chunk.getAllocateDataCount() * Float.BYTES);
        try {
            vertexCount = chunk.compile(buffer.byteBuffer());
        } catch (Exception e) {
            Openverse.logger().log(Level.SEVERE, "Error while compiling chunk (data:" + (chunk.getAllocateDataCount() * Float.BYTES) + ", cap:" + buffer.byteBuffer().capacity() + ")", e);
            buffer.release();
            return;
        }
        stateLock.lock();
        try {
            if (state == State.ABORTED) {
                buffer.release();
                return;
            }
            state = State.UPLOADING;
        } finally {
            stateLock.unlock();
        }
    }

    public void upload() {
        try {
            if (isValid()) {
                chunk.setVertices(buffer.byteBuffer(), vertexCount);
                stateLock.lock();
                try {
                    state = State.DONE;
                } finally {
                    stateLock.unlock();
                }
            }
        } finally {
            buffer.release();
        }
    }

    public void completeNow() {
        State state;
        stateLock.lock();
        try {
            state = this.state;
        } finally {
            stateLock.unlock();
        }
        switch (state) {
            case PENDING:
                compile();
                //Note the missing "break;" is not a mistake, it compiles and then uploads
            case UPLOADING:
                upload();
                break;
            default:
                break;
        }
        destroy();
    }

    public boolean isValid() {
        stateLock.lock();
        try {
            return state != State.ABORTED;
        } finally {
            stateLock.unlock();
        }
    }

    public boolean abort() {
        stateLock.lock();
        try {
            if (state != State.ABORTED) {
                state = State.ABORTED;
                return true;
            }
            if (buffer != null) {
                buffer.release();
            }
            return false;
        } finally {
            stateLock.unlock();
        }
    }

    public void destroy() {
        stateLock.lock();
        try {
            if (state != State.DONE) {
                state = State.ABORTED;
            }
            if (buffer != null) {
                buffer.release();
            }
        } finally {
            stateLock.unlock();
        }
    }


    public enum State {
        PENDING,
        COMPILING,
        UPLOADING,
        DONE,
        ABORTED
    }
}
