package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.client.render.BufferedChunk;
import xyz.upperlevel.openverse.world.chunk.ChunkSystem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RadiusSquareChunkChooser extends PlayerChunkMap {
    @Getter
    @Setter
    private int radius;

    @Getter
    private List<BufferedChunk> chunks = Collections.emptyList();

    public RadiusSquareChunkChooser(ClientWorld handle, int radius) {
        super(handle);
        this.radius = radius;
    }

    @Override
    public void setCenter(int x, int y, int z) {
        chunks = Arrays.asList(new BufferedChunk[x*y*z]);

        ChunkSystem chks = getHandle().getChunks();

        for (int ix = x - radius; ix <= x + radius; ++x)
            for (int iy = y - radius; iy <= y + radius; ++iy)
                for(int iz = z - radius; iz <= z + radius; ++iz)
                    chunks.add((BufferedChunk) chks.get(ix, iy, iz));
    }

    /*@RequiredArgsConstructor
    public class ChunkDistanceComparator implements Comparator<ChunkLoc> {

        private final int x, y, z;

        @Override
        public int compare(ChunkLoc a, ChunkLoc b) {
            if(a.equals(b))
                return 0;

            // Subtract current position to set center point
            int ax = a.x - x;
            int ay = a.y - y;
            int az = a.z - z;

            int bx = b.x - x;
            int by = b.y - y;
            int bz = b.z - z;

            //sum(a^2 - b^2) = (ax^2 - bx^2) + (ay^2 - by^2)... = ((ax-bx)*(ax+bx)) + ((ay-by)*(ay+by))...
            int result = ((ax - bx) * (ax + bx)) + ((az - bz) * (az + bz)) + ((az - bz) * (az + bz));
            if (result != 0)
                return result;

            //Last resort: y-free order
            if (ax < 0) {
                if (bx < 0)
                    return bz - az;
                else
                    return -1;
            } else {
                if (bx < 0)
                    return 1;
                else
                    return az - bz;
            }
        }
    }*/
}
