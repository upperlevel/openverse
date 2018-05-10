package xyz.upperlevel.openverse.util.math.bfs;

public interface FastFloodContext {
    boolean isOutOfBounds(int x, int y, int z);

    int getValue(int x, int y, int z);

    void setValue(int x, int y, int z, int value);

    boolean isOpaque(int x, int y, int z);
}
