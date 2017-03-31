package xyz.upperlevel.opencraft.client.render.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.render.RenderContext;
import xyz.upperlevel.opencraft.resource.block.BlockType;

import static xyz.upperlevel.opencraft.world.Chunk.*;

public class CompileChunk {

    @Getter
    private int verticesCount, drawVerticesCount;

    @Getter
    private RenderContext context;

    @Getter
    private BlockType[][][] blocks = new BlockType[WIDTH][HEIGHT][LENGTH];

    public CompileChunk(RenderContext context) {
        this.context = context;
    }

    public void setType(int x, int y, int z, BlockType type) {
        BlockType old = blocks[x][y][z];

        blocks[x][y][z] = type;

        int ovc = oldShape != null ? oldShape.getVerticesCount() : 0;
        int nvc = type != null ? shape.getVerticesCount() : 0;

        int odc = oldShape != null ? oldShape.getDataCount() : 0;
        int ndc = shape != null ? shape.getDataCount() : 0;

        allocVertCount += nvc - ovc;
        allocDataCount += ndc - odc;

        if (rebuild)
            build();
    }

    public void build() {

        context.getModelBakery().bake()
    }
}
