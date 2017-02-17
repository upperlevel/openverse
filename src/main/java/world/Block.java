package world;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Block {

    @Getter
    private final World world;

    @Getter
    private final Chunk chunk;

    @Getter
    private final int x, y, z;

    @Getter
    private final int chunkBlockX, chunkBlockY, chunkBlockZ;

    @Getter
    private BlockType type = BlockTypes.NULL_BLOCK;

    private long offset;

    private Matrix4f transformation;

    Block(Chunk chunk, int chunkBlockX, int chunkBlockY, int chunkBlockZ) {
        Objects.requireNonNull(chunk, "Chunk cannot be null.");

        world = chunk.getWorld();
        this.chunk = chunk;

        x = chunk.toWorldX(chunkBlockX);
        y = chunk.toWorldY(chunkBlockY);
        z = chunk.toWorldZ(chunkBlockZ);

        this.chunkBlockX = chunkBlockX;
        this.chunkBlockY = chunkBlockY;
        this.chunkBlockZ = chunkBlockZ;

        transformation = new Matrix4f()
                .translate(
                        new Vector3f(-1f)
                                .add(new Vector3f(
                                        ((float) chunkBlockX) / chunk.getWidth(),
                                        ((float) chunkBlockY) / chunk.getHeight(),
                                        ((float) chunkBlockZ) / chunk.getLength()
                                ).mul(2))
                                .mul(1f, 1f, -1f)
                ).scale(
                        1f / chunk.getWidth(),
                        1f / chunk.getHeight(),
                        1f / chunk.getLength()
                );
    }

    public void setType(BlockType type) {
        setType(type, false);
    }

    public void setType(BlockType type, boolean update) {
        this.type = type != null ? type : BlockTypes.NULL_BLOCK;
        if (update)
            update();
    }

    public BlockShape getShape() {
        return type.getShape();
    }

    public Block getRelative(int offsetX, int offsetY, int offsetZ) {
        return chunk.getBlock(
                x + offsetX,
                y + offsetY,
                z + offsetZ
        );
    }

    public Block getRelative(Relative relative) {
        return getRelative(
                relative.offsetX,
                relative.offsetY,
                relative.offsetZ
        );
    }

    private Set<BlockFacePosition> faces = new HashSet<>();

    public boolean isHidden() {
        return !faces.isEmpty();
    }

    public void computeHidden() {
        for (Relative relative : Relative.values())
            if (!getRelative(relative).getShape().isOccluding()) {
                faces.add(relative.asFacePosition());
                return;
            }
    }

    public void update() {
        computeHidden();
    }

    public int compile(VertexBuffer buffer, Matrix4f matrix) {
        matrix.mul(transformation);
        return type.getShape().compile(buffer, matrix);
    }
}