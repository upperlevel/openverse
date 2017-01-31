package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.render.VertexBuffer;

public class BlockFace {

    @Getter
    public final BlockComponent component;

    @Getter
    public final BlockFacePosition position;

    @Getter
    public final Zone3f zone;

    @Getter
    private FaceData data = FaceData.NULL;

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

    public BlockFace(BlockComponent component, BlockFacePosition position, FaceData data) {
        this(component, position);
        this.data = data;
    }

    public void setData(FaceData data) {
        this.data = data != null ? data : FaceData.NULL;
    }

    protected int compile(VertexBuffer buffer, Matrix4f matrix) {
        matrix.mul(transformation);

        // top left
        buffer.position(matrix.transformPosition(new Vector3f(-1f, 1f, 0f)))
                .color(1f, 0f, 0f, 1f);
        // bottom left
        buffer.position(matrix.transformPosition(new Vector3f(-1f, -1f, 0f)))
                .color(1f, 1f, 0f, 1f);
        // bottom right
        buffer.position(matrix.transformPosition(new Vector3f(1f, -1f, 0f)))
                .color(0f, 1f, 0f, 1f);
        // top right
        buffer.position(matrix.transformPosition(new Vector3f(1f, 1f, 0f)))
                .color(0f, 0f, 1f, 1f);
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
