package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.Setter;

public class Player {

    @Getter
    private double x, y, z;

    @Setter
    private float yaw, pitch;

    public void teleport(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw   = yaw;
        this.pitch = pitch;
    }
}