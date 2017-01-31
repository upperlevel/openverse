package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.render.VertexBuffer;

import java.util.Objects;

public class Block {

    @Getter
    public final World world;

    @Getter
    public final Chunk chunk;

    @Getter
    public final int x, y, z;

    @Getter
    public final int chunkBlockX, chunkBlockY, chunkBlockZ;

    @Getter
    private BlockType type = BlockTypes.NULL_BLOCK;

    @Getter
    private boolean hidden = false;

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

    public boolean isEmpty() {
        return type.empty;
    }

    public boolean isTransparent() {
        return type.transparent;
    }

    public void setType(BlockType type) {
        setType(type, false);
    }

    public void setType(BlockType type, boolean update) {
        this.type = type != null ? type : BlockTypes.NULL_BLOCK;
        if (update)
            update();
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

    public void checkHidden() {
        for (Relative relative : Relative.values())
            if (getRelative(relative).isTransparent()) {
                hidden = true;
                return;
            }
        hidden = false;
    }

    public void update() {
        checkHidden();
    }

    public int compile(VertexBuffer buffer, Matrix4f matrix) {
        matrix.mul(transformation);
        return type.shape.compile(buffer, matrix);
    }
}