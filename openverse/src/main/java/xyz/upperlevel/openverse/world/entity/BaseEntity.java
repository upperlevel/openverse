package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.EntityTeleportPacket;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

import static xyz.upperlevel.openverse.Openverse.getChannel;
import static xyz.upperlevel.openverse.Openverse.getEndpoint;

public class BaseEntity implements Entity {

    @Getter
    @Setter
    private long id = -1;

    @Getter
    private final EntityType type;

    @NonNull
    private Location location;

    @Getter
    @Setter
    @NonNull
    private Vector3f velocity = new Vector3f();

    @Getter
    @Setter
    private EntityDriver driver = new SimpleEntityDriver(); // by default

    public BaseEntity(EntityType type, Location location) {
        this.type = type;
        this.location = location;
    }

    @Override
    public World getWorld() {
        return location.getWorld();
    }

    /**
     * Returns a <b>copy</b> of its location.
     */
    @Override
    public Location getLocation() {
        return new Location(location);
    }

    public void setLocation(Location location, boolean update) {
        this.location = location;
        // todo if the location is null sends a packet that removes from the world where it is
        if (location != null && update)
            getEndpoint().getConnections().forEach(connection ->
                    connection.send(getChannel(), new EntityTeleportPacket(id, location))
            );
    }
}
