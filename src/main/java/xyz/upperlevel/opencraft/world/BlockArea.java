package xyz.upperlevel.opencraft.world;

public interface BlockArea<B> {

    int getWidth();

    int getHeight();

    int getLength();

    default boolean isOutOfRange(int x, int y, int z) {
        return x < 0 || y < 0 || z < 0 || x >= getWidth() || y >= getHeight() || z >= getLength();
    }

    B getBlock(int x, int y, int z);

    default B getRelative(BlockFace face, int x, int y, int z) {
        return getBlock(
                face.getOffsetX() + x,
                face.getOffsetY() + y,
                face.getOffsetZ() + z
        );
    }
}