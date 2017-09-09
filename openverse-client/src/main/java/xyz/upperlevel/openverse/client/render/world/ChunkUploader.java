package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.client.render.world.util.VertexBuffer;

@RequiredArgsConstructor
@Getter
public class ChunkUploader {
    private final VertexBuffer buffer;
    private final ChunkRenderer chunk;
    private final int vertexCount;

    public void upload() {
        try {
            if (chunk.isAlive()) {
                chunk.setVertices(buffer.byteBuffer(), vertexCount);
            }
        } finally {
            buffer.release();
        }
    }
}
