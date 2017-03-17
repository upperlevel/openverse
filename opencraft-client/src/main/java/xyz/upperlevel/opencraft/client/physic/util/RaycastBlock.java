package xyz.upperlevel.opencraft.client.physic.util;

import lombok.Getter;

public class RaycastBlock {

    public enum Face {
        UP,
        DOWN,

        FRONT,
        BACK,

        RIGHT,
        LEFT
    }

    @Getter
    private int x, y, z;

    @Getter
    private Face face;

    public RaycastBlock(int x, int y, int z, Face face) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.face = face;
    }
}
