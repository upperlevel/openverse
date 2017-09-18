package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import org.joml.Matrix4f;
import xyz.upperlevel.openverse.client.resource.model.shape.ClientShape;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

@Getter
public class BlockModel {
    private Set<BlockPart> parts = new HashSet<>();

    public int getVerticesCount() {
        int cnt = 0;
        for (BlockPart part : getParts())
            cnt += part.getVerticesCount();
        return cnt;
    }

    public void with(BlockPart part) {
        parts.add(part);
    }

    public void store(Matrix4f in, ByteBuffer buffer) {
        parts.forEach(part -> part.store(in, buffer));
    }
}