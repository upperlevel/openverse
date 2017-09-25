package xyz.upperlevel.openverse.client.render.block;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.joml.AABBf;
import org.joml.Matrix4f;
import xyz.upperlevel.openverse.world.chunk.Block;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class BlockModel {
    private final AABBf aabb;
    private List<BlockPart> parts;

    public BlockModel() {
        this.aabb = new AABBf();
        this.parts = new ArrayList<>();
    }

    public BlockModel(List<BlockPart> parts) {
        this();
        this.parts = parts;
    }


    public boolean testAabbCarefully(AABBf aabb) {
        for (BlockPart part : parts)
            if (part.getAabb().testAABB(aabb))
                return true;
        return false;
    }

    public void with(BlockPart part) {
        Preconditions.checkNotNull(part);
        parts.add(part);
        aabb.union(part.getAabb());
    }

    public int getVerticesCount() {
        int cnt = 0;
        for (BlockPart part : getParts())
            cnt += part.getVerticesCount();
        return cnt;
    }

    public int getDataCount() {
        return getVerticesCount() * (3 + 3);
    }

    public int store(Block block, Matrix4f in, ByteBuffer buffer) {
        int vt = 0;
        for (BlockPart p : parts) {
            vt += p.store(block, new Matrix4f(in), buffer);
        }
        return vt;
    }

    public BlockModel copy() {
        return new BlockModel(new ArrayList<>(parts));
    }
}