package xyz.upperlevel.openverse.client.render.event;

import lombok.Getter;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import java.util.*;

public class ChunkVisibilityChangeEvent implements Event {
    @Getter
    private final Set<Chunk> oldChunks, visibleChunks;

    private Set<Chunk> addedChunks, removedChunks;

    public ChunkVisibilityChangeEvent(Set<Chunk> oldChunks, Set<Chunk> newChunks) {
        this.oldChunks = Collections.unmodifiableSet(oldChunks);
        this.visibleChunks = Collections.unmodifiableSet(newChunks);
    }

    public Set<Chunk> getAddedChunks() {
        if(addedChunks == null) {
            addedChunks = new HashSet<>(visibleChunks);
            addedChunks.removeAll(oldChunks);
        }
        return addedChunks;
    }

    public Set<Chunk> getRemovedChunks() {
        if(removedChunks == null) {
            removedChunks = new HashSet<>(oldChunks);
            oldChunks.removeAll(addedChunks);
        }
        return removedChunks;
    }
}
