package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.Player;

@RequiredArgsConstructor
public abstract class PlayerChunkMap {

    @Getter
    private final ServerWorld handle;

    public abstract void onChunkChange(ChunkLocation from, ChunkLocation to, Player player);
}
