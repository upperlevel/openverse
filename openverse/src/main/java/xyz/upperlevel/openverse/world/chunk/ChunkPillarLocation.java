package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChunkPillarLocation {
    public int x, z;

    public ChunkPillarLocation(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public int hashCode() {
        return x << 16 | z;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof ChunkPillarLocation) {
            ChunkPillarLocation result = (ChunkPillarLocation) object;
            return result.x == x && result.z == z;
        }
        return super.equals(object);
    }
}