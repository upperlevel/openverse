package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.event.ChunkVisibilityChangeEvent;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.event.BlockChangeEvent;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// this class just handles rendering
public class RenderWorld implements Listener {

    @Getter
    private final World world;

    private final Map<ChunkLocation, RenderChunk> chunksByLoc = new HashMap<>();

    @Getter
    private final VisibleChunkManager visibleChunkManager;

    public RenderWorld(@NonNull World world) {
        this.world = world;
        this.visibleChunkManager = new VisibleChunkManager(world, RenderOptions.get().getRenderDistance());
        OpenverseClient.get().getEventManager().register(this);
    }

    public RenderChunk getChunk(ChunkLocation location) {
        return chunksByLoc.get(location);
    }

    public Collection<RenderChunk> getChunks() {
        return chunksByLoc.values();
    }

    public void render() {
        getChunks().forEach(RenderChunk::render);
    }

    public void destroy() {
        getChunks().forEach(RenderChunk::destroy);
        chunksByLoc.clear();
    }

    @EventHandler
    public void onChunkVisibleChange(ChunkVisibilityChangeEvent event) {
        for(Chunk chunk : event.getRemovedChunks())
            chunksByLoc.remove(chunk.getLocation());

        for(Chunk chunk : event.getAddedChunks()) {
            ChunkLocation loc = chunk.getLocation();
            chunksByLoc.put(loc, new RenderChunk(loc));
        }
    }

    @EventHandler
    public void onBlockChange(BlockChangeEvent event) {
        Block block = event.getBlock();
        Chunk chunk = block.getChunk();

        RenderChunk render = getChunk(chunk.getLocation());
        if (render == null) // if the chunk isn't loaded just skip it
            return; // todo the block must be changed when the chunk is loaded
        render.setBlockType(block);
    }
}
