package xyz.upperlevel.openverse.client.render.block;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joml.Matrix4f;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockModel {
    private Set<BlockPart> parts = new HashSet<>();

    public int getVerticesCount() {
        int cnt = 0;
        for (BlockPart part : getParts())
            cnt += part.getVerticesCount();
        return cnt;
    }

    public int getDataCount() {
        return getVerticesCount() * (3 + 3);
    }

    public void with(BlockPart part) {
        Preconditions.checkNotNull(part);
        parts.add(part);
    }

    public int store(Matrix4f in, ByteBuffer buffer) {
        int vt = 0;
        for (BlockPart p : parts) {
            vt += p.store(new Matrix4f(in), buffer);
        }
        return vt;
    }

    public BlockModel copy() {
        return new BlockModel(new HashSet<>(parts));
    }
}