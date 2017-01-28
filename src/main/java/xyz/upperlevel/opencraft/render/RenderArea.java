package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

public class RenderArea {

    @Getter
    public final WorldViewer viewer;

    @Getter
    private RenderChunkBuffer chunkBuffer;

    public RenderArea(WorldViewer viewer) {
        this.viewer = viewer;
        setRenderDistance(1);
    }

    public int getRenderDistance() {
        return chunkBuffer.rd;
    }

    public void setRenderDistance(int renderDistance) {
        if (chunkBuffer != null && chunkBuffer.rd == renderDistance)
            return;
        // initializes the new buffer and merges the old one with him
        RenderChunkBuffer newBuf = new RenderChunkBuffer(renderDistance);
        newBuf.setup(viewer.getChunk());
        if (chunkBuffer != null)
            newBuf.merge(chunkBuffer);
        chunkBuffer = newBuf;
    }

    public int getSide() {
        return chunkBuffer.side;
    }

    public int getSize() {
        int side = chunkBuffer.side;
        return side * side * side;
    }

    // cache of last center chunk coordinates
    private int
            lastCenChunkX,
            lastCenChunkY,
            lastCenChunkZ;

    public void updateBuffer() {
        Chunk center = viewer.getChunk();
        chunkBuffer.translate(
                center.x - lastCenChunkX,
                center.y - lastCenChunkY,
                center.z - lastCenChunkZ,
                center
        );
        lastCenChunkX = center.x;
        lastCenChunkY = center.y;
        lastCenChunkZ = center.z;
    }


    // todo canRender
    public void render(Uniformer uni, int v) {
        chunkBuffer.render(uni, v);
    }
}