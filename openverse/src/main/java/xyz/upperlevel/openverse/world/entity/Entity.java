package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3d;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.EntityTeleportPacket;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.event.EntityMoveEvent;

import static xyz.upperlevel.openverse.Openverse.getChannel;
import static xyz.upperlevel.openverse.Openverse.getEndpoint;

public class Entity {

    @Getter
    @Setter
    private int id = -1;

    @Getter
    private final EntityType type;

    @NonNull
    private Location location;

    @Getter
    @Setter
    @NonNull
    private Vector3d velocity = new Vector3d();

    @Getter
    @Setter
    private EntityDriver driver = new SimpleEntityDriver(); // by default

    public Entity(EntityType type, Location location) {
        this.type = type;
        this.location = location;
    }

    public World getWorld() {
        return location.getWorld();
    }

    /**
     * Returns a <b>copy</b> of its location.
     */
    public Location getLocation() {
        return new Location(location);
    }

    public void setLocation(Location location, boolean update) {
        EntityMoveEvent event = new EntityMoveEvent(this, location);
        Openverse.getEventManager().call(event);
        if(event.isCancelled()) return;

        this.location = location;
        // todo if the location is null sends a packet that removes from the world where it is
        if (location != null && update)
            getEndpoint().getConnections().forEach(connection ->
                    connection.send(getChannel(), new EntityTeleportPacket(id, location))
            );
    }

    public void setLocation(Location location) {
        setLocation(location, true);
    }

    public void setRotation(double yaw, double pitch) {
        final Location loc = getLocation();
        loc.setYaw(yaw);
        loc.setPitch(pitch);
    }
}
