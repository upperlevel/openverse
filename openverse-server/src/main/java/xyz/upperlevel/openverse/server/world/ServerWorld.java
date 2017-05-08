package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.network.SendChunkPacket;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.Player;

import static xyz.upperlevel.openverse.Openverse.getChannel;

public class ServerWorld extends World {

    @Getter
    private final PlayerChunkMap chunkMap;

    public ServerWorld(String name) {
        super(name);
        chunkMap = new RadiusSquareChunkChooser(this, 3); // depends on configured
    }

    public void onChunkChange(ChunkLocation from, ChunkLocation to, Player player) {
        chunkMap.onChunkChange(from, to, player);
    }
}
