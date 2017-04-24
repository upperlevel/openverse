package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkSystem;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RadiusSquareChunkChooser extends PlayerChunkMap {

    @Getter
    @Setter
    private int radius;

    @Getter
    private List<Chunk> chunks = Collections.emptyList();

    public RadiusSquareChunkChooser(ServerWorld handle, int radius) {
        super(handle);
        this.radius = radius;
    }

    @Override
    public void onChunkMove(ChunkLocation oldLoc, ChunkLocation newLoc, Player player) {
        Box oldBox = new Box(oldLoc.x - radius, oldLoc.y - radius, oldLoc.z - radius, radius, radius, radius);

        int ix = oldLoc.x - radius, iy = oldLoc.y + radius, iz = oldLoc.z + radius;
        int ex = oldLoc.x + radius, ey = oldLoc.y + radius, ez = oldLoc.z + radius;

        List<Chunk> chunks = new ArrayList<>();

        ChunkSystem chunkSystem = getHandle().getChunks();

        for(int x = ix; x < ex; x++)
            for(int y = iy; y < ey; y++)
                for(int z = iz; z < ez; z++)
                    if(!oldBox.isIn(x, y, z))
                        chunks.add(chunkSystem.get(x, y, z));

        for(Chunk chunk : chunks)
            ;//TODO: send chunks to player

    }
}
