package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3d;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.resource.entity.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.event.EntityMoveEvent;

import static xyz.upperlevel.openverse.Openverse.channel;
import static xyz.upperlevel.openverse.Openverse.endpoint;

@Getter
@Setter
public class Entity {
    private int id = -1;
    private final EntityType type;
    private Location location;
    private Vector3d velocity = new Vector3d();
    private EntityDriver driver = new SimpleEntityDriver(); // by default

    public Entity(EntityType type) {
        this.type = type;
    }

    public World getWorld() {
        return location.getWorld();
    }

    public void setLocation(Location location, boolean update) {
        EntityMoveEvent event = new EntityMoveEvent(this, location);
        Openverse.getEventManager().call(event);
        if(event.isCancelled()) return;

        this.location = location;
        // todo if the location is null sends a packet that removes from the world where it is
        if (location != null && update)
            endpoint().getConnections().forEach(connection ->
                    connection.send(channel(), new EntityTeleportPacket(id, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch()))
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
