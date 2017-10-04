package xyz.upperlevel.openverse.world.entity;

import com.google.common.base.Preconditions;
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

import static com.google.common.base.Preconditions.checkNotNull;
import static xyz.upperlevel.openverse.Openverse.channel;
import static xyz.upperlevel.openverse.Openverse.endpoint;

@Getter
@Setter
public class Entity {
    private int id = -1;
    private final EntityType type;
    protected Location location;
    private Vector3d velocity = new Vector3d();
    private EntityDriver driver = new SimpleEntityDriver(); // by default

    public Entity(EntityType type, Location spawn) {
        this.type = type;
        this.location = spawn;
    }

    public World getWorld() {
        return location.getWorld();
    }

    public void setLocation(Location location, boolean update) {
        checkNotNull(location);
        EntityMoveEvent event = new EntityMoveEvent(this, location);
        Openverse.getEventManager().call(event);
        if(event.isCancelled()) return;

        this.location = location;
        if (update) {
            endpoint().getConnections().forEach(connection ->
                    connection.send(channel(), new EntityTeleportPacket(id, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch()))
            );
        }
    }

    public void setLocation(Location location) {
        setLocation(location, true);
    }

    public Location getLocation() {
        return location == null ? null : new Location(location);
    }

    public void setRotation(double yaw, double pitch) {
        final Location loc = getLocation();
        loc.setYaw(yaw);
        loc.setPitch(pitch);
    }
}
