package xyz.upperlevel.opencraft.client.physic.util;

import lombok.Getter;

public class PhysicalBlock {

    @Getter
    private int x, y, z;

    @Getter
    private PhysicalFace face;

    public PhysicalBlock(int x, int y, int z, PhysicalFace face) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.face = face;
    }
}
