package xyz.upperlevel.opencraft;

import lombok.*;
import xyz.upperlevel.ulge.util.math.AngleUtil;
import xyz.upperlevel.opencraft.world.Location;
import xyz.upperlevel.opencraft.world.World;

public class EntityLocation extends Location {

    @Getter
    private double yaw, pitch;

    public EntityLocation(World world, double x, double y, double z, double yaw, double pitch) {
        super(world, x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = AngleUtil.normalizeDegAngle(yaw);
    }

    public void setPitch(double pitch) {
        this.pitch = AngleUtil.normalizeDegAngle(pitch);
    }
}