package xyz.upperlevel.openverse.world.entity;

import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

public class EntityLocation extends Location {

    public EntityLocation(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityLocation(World world, double x, double y, double z, double yaw, double pitch) {
        super(world, x, y, z, yaw, pitch);
    }

    public EntityLocation(Location location) {
        super(location);
    }


    // todo add movement methods
}
