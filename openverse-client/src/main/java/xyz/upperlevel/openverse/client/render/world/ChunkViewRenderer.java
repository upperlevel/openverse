package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.block.Facing;
import xyz.upperlevel.openverse.client.render.world.util.VertexBufferPool;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.event.ChunkLoadEvent;
import xyz.upperlevel.openverse.world.event.ChunkUnloadEvent;
import xyz.upperlevel.ulge.opengl.shader.Program;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class contains a list of all chunks that have to be rendered.
 */
@Getter
public class ChunkViewRenderer implements Listener {
    public static final int MAX_RENDER_DISTANCE = 3;

    private final Program program;
    private ClientWorld world;
    private VertexBufferPool vertexProvider = new VertexBufferPool(10);
    private ExecutorService detachedChunkCompiler = Executors.newSingleThreadExecutor(t -> new Thread(t, "Chunk Compiler thread"));
    private Queue<ChunkCompileTask> pendingTasks = new ArrayDeque<>(10);

    private int distance;
    private Map<ChunkLocation, ChunkRenderer> chunks = new HashMap<>();

    public ChunkViewRenderer(Program program) {
        this.program = program;
        this.distance = 1;
        Openverse.getEventManager().register(this);
    }

    /**
     * Memorizes {@link ChunkRenderer} and put it in a compile queue.
     * @param chunk the {@link ChunkRenderer} to memorize and compile
     */
    public void loadChunkAndCompile(ChunkRenderer chunk) {
        chunks.put(chunk.getChunk().getLocation(), chunk);
        recompileChunk(chunk, ChunkCompileMode.ASYNC);
        // compiles again all chunk relatives not null
        for (Facing facing : Facing.values()) {
            ChunkRenderer chunkRel = chunk.getChunkRelative(facing);
            if (chunkRel != null)
                recompileChunk(chunkRel, ChunkCompileMode.ASYNC);
        }
    }

    public void unloadChunk(ChunkLocation location) {
        ChunkRenderer chunk = chunks.remove(location);
        if (chunk != null)
            chunk.destroy();
    }

    public ChunkRenderer getChunk(ChunkLocation location) {
        return chunks.get(location);
    }

    public Collection<ChunkRenderer> getChunks() {
        return chunks.values();
    }

    public void setWorld(ClientWorld world) {
        this.world = world;
        //destroy();
    }

    public void recompileChunk(ChunkRenderer chunk, ChunkCompileMode mode) {
        if (mode == ChunkCompileMode.INSTANT) {
            pendingTasks.add(new ChunkCompileTask(null, chunk));
        } else {
            ChunkCompileTask task = chunk.createCompileTask(vertexProvider);
            detachedChunkCompiler.execute(() -> {
                task.compile();
                pendingTasks.add(task);
            });
        }
    }

    public void render(Program program) {
        uploadPendingChunks();
        for (ChunkRenderer chunk : chunks.values()) {
            chunk.render(program);
        }
    }

    public void uploadPendingChunks() {
        ChunkCompileTask task;
        while ((task = pendingTasks.poll()) != null) {
            task.completeNow();
        }
    }

    /**
     * Destroys all chunks and remove them from memory.
     */
    public void destroy() {
        Openverse.logger().fine("Shutting down compiler");
        detachedChunkCompiler.shutdownNow();
        Openverse.logger().fine("Done");
        chunks.values().forEach(ChunkRenderer::destroy);
        chunks.clear();
    }


    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        loadChunkAndCompile(new ChunkRenderer(this, e.getChunk(), program));
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        unloadChunk(e.getChunk().getLocation());
    }

    @EventHandler
    public void onShutdown(ShutdownEvent e) {
        destroy();
    }
}
