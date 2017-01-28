package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Relative {

    UP(0, 1, 0),
    DOWN(0, -1, 0),
    LEFT(-1, 0, 0),
    RIGHT(1, 0, 0),
    FORWARD(0, 0, 1),
    BACKWARD(0, 0, -1);

    @Getter
    public final int offsetX, offsetY, offsetZ;
}
