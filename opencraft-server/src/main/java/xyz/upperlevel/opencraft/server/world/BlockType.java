package xyz.upperlevel.opencraft.server.world;

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
