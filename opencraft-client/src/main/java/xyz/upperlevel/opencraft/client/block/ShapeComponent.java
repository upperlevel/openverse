package xyz.upperlevel.opencraft.client.block;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ShapeComponent {

    public static final int VERTICES_COUNT = BlockFace.VERTICES_COUNT * 6;

    public static final int DATA_COUNT = BlockFace.DATA_COUNT * VERTICES_COUNT;

    private final Map<BlockFacePosition, BlockFace> faces = new HashMap<>();

    @Getter
    @NonNull
    private Zone3f zone;

    private void initDefaultFaces() {
        for (BlockFacePosition position : BlockFacePosition.values())
            faces.put(position, new BlockFace(ShapeComponent.this, position));
    }

    public ShapeComponent() {
        this(new Zone3f(
                0, 0, 0,
                1f, 1f, 1f)
        );
    }

    public ShapeComponent(Zone3f zone) {
        this.zone = zone;
        initDefaultFaces();
    }

    public boolean isInside(Zone3f zone) {
        return zone.isInside(zone);
    }

    public ShapeComponent setColor(Color color) {
        faces.values().forEach(face -> face.setColor(color));
        return this;
    }

    public BlockFace getFace(BlockFacePosition position) {
        return faces.get(position); // must be present since initialized in the constructor
    }

    public Collection<BlockFace> getFaces() {
        return faces.values();
    }

    public void compileBuffer(ByteBuffer buffer, Matrix4f matrix) {
        matrix = new Matrix4f(matrix)
                .translate(zone.getSize()
                .add(zone.getMinPosition().mul(2))
                .mul(1f, 1f, -1f));
        for (BlockFace face : getFaces())
            face.compileBuffer(buffer, new Matrix4f(matrix));
    }
}