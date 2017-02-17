package world;

import lombok.*;
import org.joml.Matrix4f;

import java.nio.ByteBuffer;
import java.util.*;

public final class BlockComponent {

    public static final int SIZE = BlockFace.SIZE;

    private final Map<BlockFacePosition, BlockFace> faces = new HashMap<>();

    @Getter
    @NonNull
    private Zone3f zone;

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

    public int compile(ByteBuffer buffer, Matrix4f matrix) {
        Matrix4f m = new Matrix4f()
                .translate(zone.getSize()
                .add(zone.getMinPosition().mul(2))
                .mul(1f, 1f, -1f));
        int vertices = 0;
        for (BlockFace face : getFaces())
            vertices += face.compile(buffer, new Matrix4f(matrix));
        return vertices;
    }
}