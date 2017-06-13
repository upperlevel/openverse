package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.chunk.ChunkSystem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class RadiusSquareChunkChooser extends PlayerChunkMap {

    @Setter
    private int radius;
    private List<BufferedChunk> chunks = Collections.emptyList();

    public RadiusSquareChunkChooser(ClientWorld world, int radius) {
        super(world);
        this.radius = radius;
    }

    @Override
    public void setCenter(int x, int y, int z) {
        chunks = Arrays.asList(new BufferedChunk[x * y * z]);

        ChunkSystem chunkSys = getHandle().getChunkSystem();
        for (int ix = x - radius; ix <= x + radius; ++x)
            for (int iy = y - radius; iy <= y + radius; ++iy)
                for (int iz = z - radius; iz <= z + radius; ++iz)
                    // each time the center changes it creates a new list
                    chunks.add((BufferedChunk) chunkSys.getChunk(ix, iy, iz));
    }
}
