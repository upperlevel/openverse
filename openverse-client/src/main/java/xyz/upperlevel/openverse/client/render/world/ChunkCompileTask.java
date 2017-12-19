package xyz.upperlevel.openverse.client.render.world;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.world.util.VertexBuffer;
import xyz.upperlevel.openverse.client.render.world.util.VertexBufferPool;

import java.util.logging.Level;

@RequiredArgsConstructor
public class ChunkCompileTask {
    private final VertexBufferPool bufferPool;
    @NonNull
    private final ChunkRenderer chunk;
    private VertexBuffer buffer;
    private int vertexCount;
    private volatile State state = State.COMPILE_PENDING;

    protected void askBuffer() {
        if (buffer == null) {
            try {
                buffer = bufferPool.waitForBuffer();
            } catch (InterruptedException e) {
                Openverse.getLogger().log(Level.WARNING, " Chunk compiler: interrupted pool retrieving");
            }
        }
    }

    public boolean abortIfNecessary() {
        synchronized (this) {
            switch (state) {
                case COMPILING:
                    state = State.ABORTED;
                    return true;
                case UPLOAD_PENDING:
                    state = State.ABORTED;
                    if (buffer != null) {
                        buffer.release();
                        buffer = null;
                    }
                    return true;
                case DONE:
                case ABORTED:
                    return true;
                default:
                    return false;
            }
        }
    }

    public void abort() {
        synchronized (this) {
            switch (state) {
                case COMPILE_PENDING:
                case UPLOAD_PENDING:
                    state = State.ABORTED;
                    if (buffer != null) {
                        buffer.release();
                    }
                case COMPILING:
                    state = State.ABORTED;
            }
        }
    }

    protected void forceAbort() {
        synchronized (this) {
            if (buffer != null) {
                buffer.release();
                buffer = null;
            }
            state = State.ABORTED;
        }
    }

    public boolean compile() {
        synchronized (this) {
            if (state == State.ABORTED) {
                return false;
            }
            if (state != State.COMPILE_PENDING) {
                throw new IllegalStateException("Compile called with wrong state: " + state.name());
            }
            state = State.COMPILING;
        }
        askBuffer();
        if (buffer == null) {
            // Buffer retrieving failed (thread closed or similar reason)
            return false;
        }


        buffer.ensureCapacity(chunk.getAllocateDataCount() * Float.BYTES);
        try {
            vertexCount = chunk.compile(buffer.byteBuffer());
        } catch (Exception e) {
            Openverse.getLogger().log(Level.SEVERE, "Error while compiling chunk (data:" + (chunk.getAllocateDataCount() * Float.BYTES) + ", cap:" + buffer.byteBuffer().capacity() + ")", e);
            forceAbort();
            return false;
        }

        synchronized (this) {
            if (state == State.ABORTED) {
                buffer.release();
                buffer = null;
                return false;
            }
            if (vertexCount == 0) {
                buffer.release();
                buffer = null;
            }
            state = State.UPLOAD_PENDING;
        }
        return true;
    }

    public void upload() {
        synchronized (this) {
            if (state == State.ABORTED) {
                return;
            }
            if (state != State.UPLOAD_PENDING) {
                throw new IllegalStateException("update called with wrong state: " + state.name());
            }
            state = State.UPLOADING;
        }
        chunk.setVertices(buffer != null ? buffer.byteBuffer() : null, vertexCount);
        synchronized (this) {
            if (buffer != null) {
                buffer.release();
                buffer = null;
            }
            state = State.DONE;
        }
    }

    public void completeNow() {
        State currentState;
        synchronized (this) {
            currentState = state;
        }
        switch (currentState) {
            case COMPILE_PENDING:
                compile();
                // Note the missing "break;" is not a mistake, it compiles and then uploads
            case UPLOAD_PENDING:
                upload();
                break;
            case COMPILING:
            case UPLOADING:
                throw new IllegalStateException("Another thread is working on the task!");
        }
    }

    public boolean isDone() {
        synchronized (this) {
            return state == State.DONE;
        }
    }

    public enum State {
        COMPILE_PENDING,
        COMPILING,
        UPLOAD_PENDING,
        UPLOADING,
        DONE,
        ABORTED
    }
}
