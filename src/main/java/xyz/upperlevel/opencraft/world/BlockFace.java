package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.render.VertexBuffer;
import xyz.upperlevel.opencraft.render.texture.TextureFragment;
import xyz.upperlevel.opencraft.render.texture.Textures;
import xyz.upperlevel.ulge.util.Color;
import xyz.upperlevel.ulge.util.Colors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Accessors(chain = true)
public class BlockFace {

    @Getter
    private final BlockComponent component;

    @Getter
    private final BlockFacePosition position;

    @Getter
    private final Zone3f zone;

    @Getter
    @Setter
    @NonNull
    private TextureFragment texture = Textures.NULL;

    @Getter
    private final List<BlockVertex> vertices;

    {
        vertices =
                Arrays.stream(BlockVertexPosition.values())
                        .map(BlockVertexPosition::create)
                        .collect(Collectors.toList());
    }

    private Matrix4f transformation;

    public BlockFace(BlockComponent component, BlockFacePosition position) {
        this.component = component;
        this.zone = position.obtainZone(component.zone);
        this.position = position;

        transformation = new Matrix4f()
                .translate(position.getDirection().mul(component.zone.getSize()))
                .scale(component.zone.getSize());
        position.rotateToCubeRotation(transformation);
    }

    public void setColor(Color color) {
        vertices.forEach(vertex -> vertex.setColor(color));
    }

    public BlockVertex getVertex(BlockVertexPosition position) {
        return vertices.get(position.ordinal());
    }

    protected int compile(VertexBuffer buffer, Matrix4f matrix) {
        matrix.mul(transformation);

        /*
        BlockVertex vertex;
        float u, v;

        // top left
        vertex = getVertex(BlockVertexPosition.TOP_LEFT);
        u = texture.getMinU() + vertex.getU() * texture.getRealWidth();
        v = texture.getMinV() + vertex.getV() * texture.getRealHeight();
        buffer.position(matrix.transformPosition(vertex.getX(), vertex.getY(), vertex.getZ(), new Vector3f()))
                .color(vertex.getColor())
                .texture(u, v);
        // bottom left
        vertex = getVertex(BlockVertexPosition.BOTTOM_LEFT);
        u = texture.getMinU() + vertex.getU() * texture.getRealWidth();
        v = vertex.getV() * texture.getRealHeight();
        buffer.position(matrix.transformPosition(vertex.getX(), vertex.getY(), vertex.getZ(), new Vector3f()))
                .color(vertex.getColor())
                .texture(u, v);
        // bottom right
        vertex = getVertex(BlockVertexPosition.BOTTOM_RIGHT);
        u = vertex.getU() * texture.getRealWidth();
        v = vertex.getV() * texture.getRealHeight();
        buffer.position(matrix.transformPosition(vertex.getX(), vertex.getY(), vertex.getZ(), new Vector3f()))
                .color(vertex.getColor())
                .texture(u, v);
        // top right
        vertex = getVertex(BlockVertexPosition.TOP_RIGHT);
        u = vertex.getU() * texture.getRealWidth();
        v = texture.getMinV() + vertex.getV() * texture.getRealHeight();
        buffer.position(matrix.transformPosition(vertex.getX(), vertex.getY(), vertex.getZ(), new Vector3f()))
                .color(vertex.getColor())
                .texture(u, v);
                */

        // top left
        buffer.position(matrix.transformPosition(-1f, 1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMinU(), texture.getMinV());
        // bottom left
        buffer.position(matrix.transformPosition(-1f, -1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMinU(), texture.getMaxV());
        // bottom right
        buffer.position(matrix.transformPosition(1f, -1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMaxU(), texture.getMaxV());
        // top right
        buffer.position(matrix.transformPosition(1f, 1f, 0f, new Vector3f()))
                .color(Colors.WHITE)
                .texture(texture.getMaxU(), texture.getMinV());

        System.out.println(texture.toString());
        return 4;
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
