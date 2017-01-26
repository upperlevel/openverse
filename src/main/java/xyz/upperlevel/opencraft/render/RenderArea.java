package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.Chunk;

public class RenderArea {

    @Getter
    public final WorldViewer viewer;

    private RenderChunkBuffer data;

    public RenderArea(WorldViewer viewer) {
        this.viewer = viewer;
        setRenderDistance(1);
    }

    public int getRenderDistance() {
        return data.renderDistance;
    }

    public void setRenderDistance(int renderDistance) {
        if (data != null && data.renderDistance == renderDistance)
            return;
        // initializes the new buffer and merges the old one with him
        RenderChunkBuffer newBuf = new RenderChunkBuffer(renderDistance);
        newBuf.setupBuffer(viewer.getChunk());
        if (data != null)
            newBuf.mergeBuffer(data);
        data = newBuf;
    }

    // cache of last center chunk coordinates
    private int
            lastCenChunkX,
            lastCenChunkY,
            lastCenChunkZ;

    public void updateBuffer() {
        Chunk center = viewer.getChunk();
        data.translateBuffer(
                center.x - lastCenChunkX,
                center.y - lastCenChunkY,
                center.z - lastCenChunkZ,
                center
        );
        lastCenChunkX = center.x;
        lastCenChunkY = center.y;
        lastCenChunkZ = center.z;
    }
}