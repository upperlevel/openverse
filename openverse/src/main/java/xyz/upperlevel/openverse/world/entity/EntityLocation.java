package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.ulge.util.math.CameraUtil;

import static xyz.upperlevel.ulge.util.math.CameraUtil.getForward;

public class EntityLocation<W extends World> extends Location<W> {

    public EntityLocation(W world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityLocation(W world, double x, double y, double z, double yaw, double pitch) {
        super(world, x, y, z, yaw, pitch);
    }

    public EntityLocation(Location<W> location) {
        super(location);
    }

    // todo add move methods
}
