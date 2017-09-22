package xyz.upperlevel.openverse.client.render.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.util.config.Config;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class BlockPartFace {
    private final Facing facing;
    private final Path textureLocation;
    private final Vertex[] vertices = new Vertex[4];
    private int textureLayer = -1;

    private void setupVertices() {
        for (VertexPosition pos : VertexPosition.values())
            vertices[pos.ordinal()] = new Vertex(pos);
    }

    public BlockPartFace(Facing facing, Config config) {
        this.facing = facing;
        this.textureLocation = Paths.get(config.getString("texture"));
        setupVertices();
    }

    public int store(Matrix4f transform, ByteBuffer buffer) {
        transform
                // put the face in the right cube position
                .translate(facing.getDir())
                // rotates the face to its position
                .translate(.5f, .5f, .5f)
                .rotate(facing.getRot())
                .translate(-.5f, -.5f, -.5f);
        final int layer = getTextureLayer();
        int vrt = 0;
        for (Vertex v : vertices)
            vrt += v.store(transform, buffer, layer);
        return vrt;
    }

    public int getTextureLayer() {
        if (textureLayer == -1) {
            textureLayer = TextureBakery.getLayer(textureLocation);
        }
        return textureLayer;
    }

    public static BlockPartFace deserialize(Facing facing, Config config) {
        return new BlockPartFace(facing, config);
    }

    @Getter
    @RequiredArgsConstructor
    public enum VertexPosition {
        TOP_LEFT     (0, 1, 0,  0, 0),
        TOP_RIGHT    (1, 1, 0,  1, 0),
        BOTTOM_RIGHT (1, 0, 0,  1, 1),
        BOTTOM_LEFT  (0, 0, 0,  0, 1);

        public final int x, y, z, u, v;
    }

    @Getter
    @AllArgsConstructor
    public static class Vertex {
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
