package xyz.upperlevel.opencraft.client.asset.shape;

public enum BlockFaceVertexPosition {

    TOP_LEFT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(-1f)
                    .setY(1f);
        }
    },
    TOP_RIGHT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(1f)
                    .setY(1f);
                    //.setU(1f);
        }
    },
    BOTTOM_LEFT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(-1f)
                    .setY(-1f);
                    //.setV(1f);
        }
    },
    BOTTOM_RIGHT {
        @Override
        public BlockVertex createVertex(BlockFace face) {
            return new BlockVertex(face)
                    .setX(1f)
                    .setY(-1f);
                    //.setU(1f)
                    //.setV(1f);
        }
    };

    public abstract BlockVertex createVertex(BlockFace face);
}
