package xyz.upperlevel.opencraft.client.asset.shape;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.OpenCraft;
import xyz.upperlevel.opencraft.client.asset.texture.Texture;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

import static xyz.upperlevel.opencraft.client.asset.shape.BlockVertexBufferStorer.*;

@Accessors(chain = true)
public class BlockFace {

    public static final int VERTICES_COUNT = BlockVertex.VERTICES_COUNT * 4;

    public static final int DATA_COUNT = BlockVertex.DATA_COUNT * VERTICES_COUNT;

    @Getter
    private BlockCubeComponent component;

    @Getter
    private BlockFacePosition position;

    @Getter
    private Zone3f zone, mirrorZone;

    @Getter
    @Setter
    private Texture texture = OpenCraft.get().getAssetManager().getTextureManager().getTexture("null_texture");

    @Getter
    private BlockVertex[] vertices = new BlockVertex[4];

    {
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = BlockFaceVertexPosition.values()[i].createVertex(this);
    }

    public BlockFace(BlockCubeComponent component, BlockFacePosition position) {
        this.component = component;

        this.zone = position.getZone(component.getZone());
        mirrorZone = position.getMirrorZone(zone);

        this.position = position;
    }

    public BlockVertex getVertex(BlockFaceVertexPosition position) {
        return vertices[position.ordinal()];
    }

    public void setColor(Color color) {
        for (BlockVertex blockVertex : vertices)
            blockVertex.setColor(color);
    }

    public void compile(Matrix4f matrix, ByteBuffer buffer) {
        Zone3f component = this.component.getZone();

        matrix = new Matrix4f(matrix)
                .translate(position.getDirection().mul(component.getSize()))
                .scale(component.getSize())
                .rotate(position.getRotation());

        BlockVertex vertex;

        // top left
        vertex = getVertex(BlockFaceVertexPosition.TOP_LEFT);
        putPosition(buffer, matrix.transformPosition(-1f, 1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMinU(), texture.getMinV());

        // bottom left
        vertex = getVertex(BlockFaceVertexPosition.BOTTOM_LEFT);
        putPosition(buffer, matrix.transformPosition(-1f, -1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMinU(), texture.getMaxV());

        // bottom right
        vertex = getVertex(BlockFaceVertexPosition.BOTTOM_RIGHT);
        putPosition(buffer, matrix.transformPosition(1f, -1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMaxU(), texture.getMaxV());

        // top right
        vertex = getVertex(BlockFaceVertexPosition.TOP_RIGHT);
        putPosition(buffer, matrix.transformPosition(1f, 1f, 0f, new Vector3f()));
        putColor(buffer, vertex.getColor());
        putTextureCoordinates(buffer, texture.getMaxU(), texture.getMinV());
    }
}
