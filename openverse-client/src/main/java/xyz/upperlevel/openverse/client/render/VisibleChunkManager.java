package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.event.ChunkVisibilityChangeEvent;
import xyz.upperlevel.openverse.client.render.event.RenderOptionsChangeEvent;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkSystem;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

import java.util.*;

public class VisibleChunkManager implements Listener {

    @Getter
    private final World world;

    private int radius;
    private Set<Chunk> chunks = Collections.emptySet();

    private ChunkLocation center;

    public VisibleChunkManager(World world, int radius) {
        this.world = world;
        this.radius = radius;

        Openverse.getEventManager().register(this);
    }

    public void setCenter(ChunkLocation c) {
        Set<Chunk> oldChunks = chunks;
        chunks = new HashSet<>(radius * radius * radius);

        ChunkSystem chunkSys = world.getChunkSystem();
        for (int ix = c.x - radius; ix <= c.x + radius; ++ix)
            for (int iy = c.y - radius; iy <= c.y + radius; ++iy)
                for (int iz = c.z - radius; iz <= c.z + radius; ++iz)
                    // each time the center changes it creates a new list
                    chunks.add(chunkSys.getChunk(ix, iy, iz));
        Openverse.getEventManager().call(new ChunkVisibilityChangeEvent(oldChunks, chunks));
        this.center = c;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        setCenter(center);//reload chunks
    }

    public Set<Chunk> getChunks() {
        return Collections.unmodifiableSet(chunks);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(!event.isCancelled()) {
            Chunk c = event.getLocation().getChunk();
            if(c != event.getOldLocation().getChunk())
                setCenter(c.getLocation());
        }
    }

    @EventHandler
    public void onRenderOptionsChange(RenderOptionsChangeEvent event) {
        if(!event.isCancelled()) {
            int renderDistance = event.getOptions().getRenderDistance();
            if(renderDistance != event.getOldOptions().getRenderDistance())
                setRadius(renderDistance);
        }
    }
}
