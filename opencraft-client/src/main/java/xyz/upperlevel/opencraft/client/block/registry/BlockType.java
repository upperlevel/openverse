package xyz.upperlevel.opencraft.client.block.registry;

import xyz.upperlevel.opencraft.client.block.BlockShape;

public interface BlockType {

    BlockType NULL = new BlockType() {
        @Override
        public String getId() {
            return "null";
        }

        @Override
        public BlockShape getShape() {
            return new BlockShape();
        }
    };

    String getId();

    BlockShape getShape();

    static BlockType create(String id, BlockShape shape) {
        return new BlockType() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public BlockShape getShape() {
                return shape;
            }
        };
    }
}
