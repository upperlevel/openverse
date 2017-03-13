package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;

public class BlockRenderer {

    @Getter
    private ViewRenderer view;
    
    @Getter
    private ChunkRenderer chunk;
    
    @Getter
    private int chunkX, chunkY, chunkZ;

    @Getter
    private int viewX, viewY, viewZ;

    @Getter
    @Setter
    @NonNull
    private BlockShape shape = null;

    public BlockRenderer(ChunkRenderer chunk, int chunkX, int chunkY, int chunkZ) {
        view       = chunk.getView();
        this.chunk = chunk;
        
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
        
        viewX = chunk.getX() * 16 + chunkX;
        viewY = chunk.getY() * 16 + chunkY;
        viewZ = chunk.getZ() * 16 + chunkZ;
    }

    public BlockRenderer(ViewRenderer view, int viewX, int viewY, int viewZ) {
        this.view = view;
        chunk     = view.getChunk(
                viewX / 16,
                viewY / 16,
                viewZ / 16
        );

        chunkX = viewX % 16;
        chunkY = viewY % 16;
        chunkZ = viewZ % 16;

        this.viewX = viewX;
        this.viewY = viewY;
        this.viewZ = viewZ;
    }

    public boolean isEmpty() {
        return shape == null || shape.isEmpty();
    }
}
