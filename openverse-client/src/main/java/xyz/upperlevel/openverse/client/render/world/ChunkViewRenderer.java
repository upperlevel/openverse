package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.world.util.VertexBufferPool;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.event.BlockUpdateEvent;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.event.ChunkLightChangeEvent;
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

    private final OpenverseClient client;

    private final Program program;
    private ClientWorld world;
    private VertexBufferPool vertexProvider = new VertexBufferPool(50);
    private VertexBufferPool syncProvider = new VertexBufferPool(1);
    private ExecutorService detachedChunkCompiler = Executors.newSingleThreadExecutor(t -> new Thread(t, "Chunk Compiler thread"));
    private Queue<ChunkCompileTask> pendingTasks = new ArrayDeque<>(10);

    private int distance;
    private Map<ChunkLocation, ChunkRenderer> chunks = new HashMap<>();

    public ChunkViewRenderer(OpenverseClient client, Program program) {
        this.client = client;

        this.program = program;
        this.distance = 1;

        client.getEventManager().register(this);
    }

    public void loadChunk(ChunkRenderer chunk) {
        ChunkRenderer previous = chunks.put(chunk.getChunk().getLocation(), chunk);
        if (previous != null) {
            previous.destroy();
            throw new IllegalStateException("Chunk Renderer already loaded: " + chunk.getChunk().getLocation());
        }
    }

    public void unloadChunk(ChunkLocation location) {
        ChunkRenderer chunk = chunks.remove(location);
        if (chunk != null) {
            chunk.destroy();
        }
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
            pendingTasks.add(new ChunkCompileTask(client, syncProvider, chunk));
        } else {
            ChunkCompileTask task = chunk.createCompileTask(vertexProvider);
            detachedChunkCompiler.execute(() -> {
                if (task.compile()) {
                    // If the task compiled successfully
                    // Put it in the uploading queue
                    pendingTasks.add(task);
                }
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

    public void recompileChunksAround(int x, int y, int z, ChunkCompileMode mode) {
        for (int chkX = x - 1; chkX <= x + 1; chkX++) {
            for (int chkY = y - 1; chkY <= y + 1; chkY++) {
                for (int chkZ = z - 1; chkZ <= z + 1; chkZ++) {
                    ChunkRenderer chunk = getChunk(ChunkLocation.of(chkX, chkY, chkZ));
                    if (chunk == null) {
                        continue;
                    }
                    recompileChunk(chunk, mode);
                }
            }
        }
    }

    public void recompileChunksAroundBlock(int x, int y, int z, ChunkCompileMode mode) {
        int minX = (x - 1) >> 4;
        int minY = (y - 1) >> 4;
        int minZ = (z - 1) >> 4;
        int maxX = (x + 1) >> 4;
        int maxY = (y + 1) >> 4;
        int maxZ = (z + 1) >> 4;
        for (int chkX = minX; chkX <= maxX; chkX++) {
            for (int chY = minY; chY <= maxY; chY++) {
                for (int chZ = minZ; chZ <= maxZ; chZ++) {
                    ChunkRenderer chunk = getChunk(ChunkLocation.of(chkX, chY, chZ));
                    if (chunk == null) {
                        continue;
                    }
                    recompileChunk(chunk, mode);
                }
            }
        }
    }

    /**
     * Destroys all chunks and remove them from memory.
     */
    public void destroy() {
        client.getLogger().fine("Shutting down chunk compiler");
        detachedChunkCompiler.shutdownNow();
        client.getLogger().fine("Done");
        chunks.values().forEach(ChunkRenderer::destroy);
        chunks.clear();
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        loadChunk(new ChunkRenderer(client,this, e.getChunk(), program));

        ChunkLocation loc = e.getChunk().getLocation();
        recompileChunksAround(loc.x, loc.y, loc.z, ChunkCompileMode.ASYNC);
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        unloadChunk(e.getChunk().getLocation());
    }

    @EventHandler
    public void onBlockUpdate(BlockUpdateEvent e) {
        if (world != e.getWorld()) {
            return;
        }
        recompileChunksAroundBlock(e.getX(), e.getY(), e.getZ(), ChunkCompileMode.INSTANT);
    }


    @EventHandler
    public void onChunkLightChange(ChunkLightChangeEvent e) {
        if (world != e.getWorld()) {
            return;
        }
        recompileChunk(getChunk(e.getChunk().getLocation()), ChunkCompileMode.ASYNC);
    }

    @EventHandler
    public void onShutdown(ShutdownEvent e) {
        destroy();
    }
}
