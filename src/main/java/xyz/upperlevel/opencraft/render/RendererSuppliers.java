package xyz.upperlevel.opencraft.render;

import xyz.upperlevel.opencraft.render.block.BlockRenderer;
import xyz.upperlevel.opencraft.render.block.BlockRendererSupplier;
import xyz.upperlevel.opencraft.render.chunk.ChunkRendererSupplier;
import xyz.upperlevel.opencraft.world.Block;

public final class RendererSuppliers {

    private RendererSuppliers() {
    }

    public static final RendererSupplier<BlockRenderer> BLOCK = new RendererSupplier<>();

    public static final ChunkRendererSupplier CHUNK = new ChunkRendererSupplier();
}
