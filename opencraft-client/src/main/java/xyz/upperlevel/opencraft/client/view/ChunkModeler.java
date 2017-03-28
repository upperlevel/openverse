package xyz.upperlevel.opencraft.client.view;

import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.common.block.BlockType;
import xyz.upperlevel.opencraft.common.world.ChunkData;

public class ChunkModeler implements ChunkData {

    private BlockType[][][] blocks = new BlockType[WIDTH][HEIGHT][LENGTH];

    private int vc, dc;

    public ChunkModeler() {
    }

    @Override
    public BlockType getType(int x, int y, int z) {
        return blocks[x][y][z];
    }

    @Override
    public void setType(int x, int y, int z, BlockType type) {
        BlockType ot = blocks[x][y][z];
        BlockShape os = ot != null ? ot.getModel() : null;

        blocks[x][y][z] = type;

        int ovc = oldShape != null ? oldShape.getVerticesCount() : 0;
        int nvc = shape != null ? shape.getVerticesCount() : 0;

        int odc = oldShape != null ? oldShape.getDataCount() : 0;
        int ndc = shape != null ? shape.getDataCount() : 0;

        allocVertCount += nvc - ovc;
        allocDataCount += ndc - odc;

        if (rebuild)
            build();
    }
}
