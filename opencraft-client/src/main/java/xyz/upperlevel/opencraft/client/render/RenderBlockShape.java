package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.block.BlockShape;

public class RenderBlockShape {

    @Getter
    private int x, y, z;

    @Getter
    private RenderChunk chunk;

    @Getter
    private BlockShape shape;

    @Getter
    private boolean hidden = false;

    public RenderBlockShape(RenderChunk chunk, int x, int y, int z) {
        this(chunk, x, y, z, null);
    }

    public RenderBlockShape(RenderChunk chunk, int x, int y, int z, BlockShape shape) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunk = chunk;
        this.shape = shape;
    }

    public RenderBlockShape relative(int x, int y, int z) {
        int rx = this.x + x;
        int ry = this.y + y;
        int rz = this.z + z;

        if (rx < 0 || rx >= 16 || ry < 0 || ry >= 16 || rz < 0 || rz >= 16)
            return new RenderBlockShape(chunk, rx, ry, rz);
        return chunk.getShape(rx, ry, rz);
    }

    public void setShape(BlockShape shape) {
        this.shape = shape;

        /*
        relative(0, 1, 0).calculateHidden();
        relative(0, -1, 0).calculateHidden();


        relative(1, 0, 0).calculateHidden();
        relative(-1, 0, 0).calculateHidden();

        relative(0, 0, 1).calculateHidden();
        relative(0, 0, -1).calculateHidden();
        */
    }

    public void calculateHidden() {
        RenderBlockShape relative;

        relative = relative(0, 1, 0);
        if (relative.shape == null || !relative.shape.isOccluding()) {
            hidden = false;
            return;
        }

        relative = relative(0, -1, 0);
        if (relative.shape == null || !relative.shape.isOccluding()) {
            hidden = false;
            return;
        }

        relative = relative(1, 0, 0);
        if (relative.shape == null || !relative.shape.isOccluding()) {
            hidden = false;
            return;
        }

        relative = relative(-1, 0, 0);
        if (relative.shape == null || !relative.shape.isOccluding()) {
            hidden = false;
            return;
        }

        relative = relative(0, 0, 1);
        if (relative.shape == null || !relative.shape.isOccluding()) {
            hidden = false;
            return;
        }

        relative = relative(0, 0, -1);
        if (relative.shape == null || !relative.shape.isOccluding()) {
            hidden = false;
            return;
        }

        hidden = true;
    }
}