package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class PlayerChunkMap {

    @Getter
    private final ClientWorld handle;

    public abstract void setCenter(int x, int y, int z);

    public abstract List<BufferedChunk> getChunks();
}
