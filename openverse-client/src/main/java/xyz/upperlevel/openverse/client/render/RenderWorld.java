package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.event.BlockChangeEvent;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.event.ChunkCreateEvent;
import xyz.upperlevel.openverse.world.chunk.event.ChunkDeleteEvent;

import java.util.HashMap;
import java.util.Map;

// this class just handles rendering
public class RenderWorld implements Listener {

    @Getter
    private final String name;

    private final Map<ChunkLocation, RenderChunk> chunksByLoc = new HashMap<>();

    public RenderWorld(@NonNull OpenverseClient client, @NonNull String name) {
        this.name = name;
        client.getEventManager().register(this);
    }

    @EventHandler
    public void onChunkCreate(ChunkCreateEvent event) {
        Chunk ck = event.getChunk();
        ChunkLocation loc = ck.getLocation();

        chunksByLoc.put(ck.getLocation(), new RenderChunk(loc));
    }

    @EventHandler
    public void onChunkDelete(ChunkDeleteEvent event) {
        chunksByLoc.remove(event.getChunk().getLocation());
    }

    @EventHandler
    public void onBlockChange(BlockChangeEvent event) {
        Block block = event.getBlock();
        Chunk chunk = block.getChunk();

        RenderChunk render = chunksByLoc.get(chunk.getLocation());
        if (render == null)
            return; // if the chunk isn't found just skip it

        render.setBlockType(block);
    }

    public void clear() {
        chunksByLoc.values().forEach(RenderChunk::clear);
    }

    public void render() {
        chunksByLoc.values().forEach(RenderChunk::render);
    }

    public void destroy() {
        chunksByLoc.values().forEach(RenderChunk::destroy);

        chunksByLoc.clear();
    }
}
