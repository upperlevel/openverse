package xyz.upperlevel.openverse.client.render.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.AABBf;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.world.chunk.Block;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@RequiredArgsConstructor
public class BlockPartFace {
    private final BlockPart part;
    private final Facing facing;
    private final AABBf aabb;
    private final AABBf mirroredAabb;

    private final Path textureLocation;
    private final Vertex[] vertices = new Vertex[4];
    private int textureLayer = -1;

    private void setupVertices() {
        for (VertexPosition pos : VertexPosition.values())
            vertices[pos.ordinal()] = new Vertex(pos);
    }

    public BlockPartFace(BlockPart part, Facing facing, Config config) {
        this.part = part;
        this.facing = facing;
        this.aabb = facing.resolveAabb(part.getAabb());
        this.mirroredAabb = facing.mirrorAabb(aabb);

        this.textureLocation = Paths.get(config.getString("texture"));
        setupVertices();
    }

    public int store(Block block, Matrix4f transform, ByteBuffer buffer) {
        Block rel = block.getRelative(facing.dirX, facing.dirY, facing.dirZ);
        BlockModel neighbor = rel == null ? null : BlockTypeModelMapper.model(rel.getState());
        // checks if the face is hidden
        if (neighbor != null && neighbor.getAabb().testAABB(mirroredAabb) && neighbor.testAabbCarefully(mirroredAabb)) {
            return 0;
        } else {
            transform
                    .translate(facing.getDir())
                    .translate(.5f, .5f, .5f)
                    .rotate(facing.getRot())
                    .translate(-.5f, -.5f, -.5f);
            int vrt = 0;
            int layer = getTextureLayer();
            for (Vertex v : vertices)
                vrt += v.store(transform, buffer, layer);
            return vrt;
        }
    }

    public int getTextureLayer() {
        if (textureLayer == -1) {
            textureLayer = TextureBakery.getLayer(textureLocation);
        }
        return textureLayer;
    }

    public static BlockPartFace deserialize(BlockPart part, Facing facing, Config config) {
        return new BlockPartFace(part, facing, config);
    }

    @Getter
    @RequiredArgsConstructor
    public enum VertexPosition {
        TOP_LEFT(0, 1, 0, 0, 0),
        TOP_RIGHT(1, 1, 0, 1, 0),
        BOTTOM_RIGHT(1, 0, 0, 1, 1),
        BOTTOM_LEFT(0, 0, 0, 0, 1);

        public final int x, y, z, u, v;
    }

    @Getter
    @AllArgsConstructor
    public class Vertex {
        private final VertexPosition position;

        public int store(Matrix4f transform, ByteBuffer buffer, int layer) {
            Vector3f pos = transform.transformPosition(position.x, position.y, position.z, new Vector3f());
            buffer
                    .putFloat(pos.x)
                    .putFloat(pos.y)
                    .putFloat(pos.z)
                    .putFloat(position.u)
                    .putFloat(position.v)
                    .putFloat(layer);
            return 1;
        }
    }
}
