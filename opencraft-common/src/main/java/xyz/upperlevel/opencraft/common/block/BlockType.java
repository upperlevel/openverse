package xyz.upperlevel.opencraft.common.block;

public interface BlockType {

    BlockType NULL = new BlockType() {
        @Override
        public String getId() {
            return "null";
        }

        @Override
        public BlockState generateState() {
            return BlockState.NULL;
        }
    };

    String getId();

    BlockState generateState();
}
