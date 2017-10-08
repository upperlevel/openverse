package xyz.upperlevel.openverse;

import org.junit.Assert;
import org.junit.Test;
import xyz.upperlevel.openverse.world.chunk.SimpleChunkPillarProvider;

import java.util.HashSet;
import java.util.Set;

public class ChunkPillarCoordTest {

    @Test
    public void simpleCoordsCompressionTest() {
        int testRadius = 16;
        Set<Long> locations = new HashSet<>();
        for (int x = -testRadius; x < testRadius; x++) {
            for (int z = -testRadius; z < testRadius; z++) {
                long i = SimpleChunkPillarProvider.provideIndex(x, z);
                if (!locations.add(i)) {
                    Assert.fail("Multiple adding: x=" + x + " z=" + z + " id=" + i + " id_bin=" + Long.toHexString(i));
                }
            }
        }
    }
}
