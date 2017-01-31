package xyz.upperlevel.opencraft.world;

import lombok.*;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.render.VertexBuffer;

import java.util.*;

public class BlockComponent {

    private final Map<BlockFacePosition, BlockFace> faces = new HashMap<>();

    @Getter
    @NonNull
    public final Zone3f zone;

    private Matrix4f transformation;

    private void initDefaultFaces() {
        for (BlockFacePosition position : BlockFacePosition.values())
            faces.put(position, new BlockFace(BlockComponent.this, position));
    }

    public BlockComponent() {
        this(new Zone3f(
                0, 0, 0,
                1f, 1f, 1f)
        );
    }

    public BlockComponent(Zone3f zone) {
        this.zone = zone;
        initDefaultFaces();

        transformation = new Matrix4f().translate(zone.getSize()
                .add(zone.getMinPosition().mul(2))
                .mul(1f, 1f, -1f)
        );
    }

    public boolean isInside(Zone3f zone) {
        return zone.isInside(zone);
    }

    public BlockFace getFace(BlockFacePosition position) {
        return faces.get(position); // must be present since initialized in the constructor
    }

    public Collection<BlockFace> getFaces() {
        return faces.values();
    }

    protected int compile(VertexBuffer buffer, Matrix4f matrix) {
        matrix.mul(transformation);
        int vertices = 0;
        for (BlockFace face : getFaces())
            vertices += face.compile(buffer, new Matrix4f(matrix));
        return vertices;
    }
}