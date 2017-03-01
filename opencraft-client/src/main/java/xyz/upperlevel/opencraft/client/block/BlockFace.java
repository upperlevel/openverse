package xyz.upperlevel.opencraft.client.block;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.texture.TextureFragment;
import xyz.upperlevel.opencraft.client.texture.Textures;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

import static xyz.upperlevel.opencraft.client.block.BlockVertexBufferStorer.putColor;
import static xyz.upperlevel.opencraft.client.block.BlockVertexBufferStorer.putPosition;
import static xyz.upperlevel.opencraft.client.block.BlockVertexBufferStorer.putTextureCoordinates;

enum QuadVertexPosition {
    TOP_LEFT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(-1f)
                    .setY(1f);
        }
    },
    TOP_RIGHT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(1f)
                    .setY(1f)
                    .setU(1f);
        }
    },
    BOTTOM_LEFT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(-1f)
                    .setY(-1f)
                    .setV(1f);
        }
    },
    BOTTOM_RIGHT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(1f)
                    .setY(-1f)
                    .setU(1f)
                    .setV(1f);
        }
    };

    public abstract BlockVertex createVertex(BlockFace face);
}

@Accessors(chain = true)
public final class BlockFace {

    public static final int VERTICES_COUNT = BlockVertex.VERTICES_COUNT * 4;

    public static final int DATA_COUNT = BlockVertex.DATA_COUNT * VERTICES_COUNT;

    @Getter
    private ShapeComponent component;

    @Getter
    private BlockFacePosition position;

    @Getter
    private Zone3f zone;

    @Getter
    @Setter
    @NonNull
    private TextureFragment texture = Textures.NULL;

    @Getter
    private BlockVertex[] vertices = new BlockVertex[4];

    {
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = QuadVertexPosition.values()[i].createVertex(this);
    }

    public BlockFace(ShapeComponent component, BlockFacePosition position) {
        this.component = component;
        this.zone = position.obtainZone(component.getZone());
        this.position = position;
    }

    public BlockVertex getVertex(QuadVertexPosition position) {
        return vertices[position.ordinal()];
    }

    public void setColor(Color color) {
        for (BlockVertex blockVertex : vertices)
            blockVertex.setColor(color);
    }

    public void compileBuffer(ByteBuffer buffer, Matrix4f matrix) {
        Zone3f component = this.component.getZone();

        /*
        Matrix4f m = position.rotateToCubeRotation(new Matrix4f(matrix)
                .translate(position.getDirection().mul(compZone.getSize()))
                .scale(compZone.getSize()));
*/
        matrix = new Matrix4f(matrix)
                .translate(position.getDirection().mul(component.getSize()))
                .scale(component.getSize())
                .rotate(position.getRotation());

        BlockVertex vertex;

        // top left
        vertex = getVertex(QuadVertexPosition.TOP_LEFT);
        putPosition(buffer, matrix.transformPosition(-1f, 1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMinU(), texture.getMinV());

        // bottom left
        vertex = getVertex(QuadVertexPosition.BOTTOM_LEFT);
        putPosition(buffer, matrix.transformPosition(-1f, -1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMinU(), texture.getMaxV());

        // bottom right
        vertex = getVertex(QuadVertexPosition.BOTTOM_RIGHT);
        putPosition(buffer, matrix.transformPosition(1f, -1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMaxU(), texture.getMaxV());

        // top right
        vertex = getVertex(QuadVertexPosition.TOP_RIGHT);
        putPosition(buffer, matrix.transformPosition(1f, 1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMaxU(), texture.getMinV());

        /*
        buffer.position(m.transformPosition(-1f, 1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMinU(), texture.getMinV());
        // bottom left
        buffer.position(m.transformPosition(-1f, -1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMinU(), texture.getMaxV());
        // bottom right
        buffer.position(m.transformPosition(1f, -1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMaxU(), texture.getMaxV());
        // top right
        buffer.position(m.transformPosition(1f, 1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMaxU(), texture.getMinV());
                */
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BlockFace && position == ((BlockFace) object).position;
    }
}