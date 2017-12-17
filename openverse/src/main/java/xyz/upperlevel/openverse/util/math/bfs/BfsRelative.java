package xyz.upperlevel.openverse.util.math.bfs;

import lombok.Getter;

@Getter
public enum BfsRelative {
    UP      ( 0,  1,  0),
    DOWN    ( 0, -1,  0),
    LEFT    (-1,  0,  0),
    RIGHT   ( 1,  0,  0),
    FORWARD ( 0,  0, -1),
    BACK    ( 0,  0,  1);

    private final int offsetX, offsetY, offsetZ;

    BfsRelative(int offsetX, int offsetY, int offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }
}
