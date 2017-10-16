package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3d;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.util.math.Aabb3d;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.event.EntityMoveEvent;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static xyz.upperlevel.openverse.Openverse.channel;
import static xyz.upperlevel.openverse.Openverse.endpoint;

public class Entity {
    @Getter
    @Setter
    private int id = -1;
    @Getter
    private final EntityType type;
    @Getter
    private float width;
    @Getter
    private float height;
    @Getter
    private boolean onGround;

    protected Location location;
    @Getter
    @Setter
    private Vector3d velocity = new Vector3d();
    @Getter
    @Setter
    private Aabb3d boundingBox = Aabb3d.ZERO;

    public Entity(EntityType type, Location spawn) {
        this.type = type;
        this.location = spawn;
    }

    public World getWorld() {
        return location.getWorld();
    }

    public void setLocation(Location location) {
        checkNotNull(location);
        EntityMoveEvent event = new EntityMoveEvent(this, location);
        Openverse.getEventManager().call(event);
        if (event.isCancelled()) return;
        this.location = location;
    }

    protected void updateBoundingBox() {
        if (location == null) {
            return;
        }
        double w = width / 2.0;
        setBoundingBox(new Aabb3d(
                location.getX() - w, location.getY(), location.getZ() - w,
                location.getX() + w, location.getY() + height, location.getZ() + w
        ));
    }

    public Location getLocation() {
        return location == null ? null : new Location(location);
    }

    public void setRotation(double yaw, double pitch) {
        final Location loc = getLocation();
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        setLocation(loc);
    }

    public void setSize(float width, float height) {
        if (width != this.width || height != this.height) {
            this.width = width;
            this.height = height;
            updateBoundingBox();
        }
    }

    public void updateFalling(double yVel) {
        //TODO: add fall on block event or fall distance
    }

    public void move(double movX, double movY, double movZ) {
        double oldMovX = movX;
        double oldMovY = movY;
        double oldMovZ = movZ;

        Aabb3d box = getBoundingBox();
        List<Aabb3d> collisions = getWorld().getBlockCollisions(this, box.grow(movX, movY, movZ));

        if (movX != 0) {
            //Get the max movement
            for (Aabb3d b : collisions) {
                movX = b.getOverlapX(box, movX);
            }
            //Move
            box = box.translate(movX, 0.0, 0.0);
        }

        if (movY != 0) {
            //Get the max movement
            for (Aabb3d b : collisions) {
                movY = b.getOverlapY(box, movY);
            }
            //Move
            box = box.translate(0.0, movY, 0.0);
        }

        if (movZ != 0) {
            //Get the max movement
            for (Aabb3d b : collisions) {
                movZ = b.getOverlapZ(box, movZ);
            }
            //Move
            box = box.translate(0.0, 0.0, movZ);
        }


        setBoundingBox(box);
        location.add(movX, movY, movZ);
        //TODO: add step (like ladders)

        onGround = movY < 0.0 && (oldMovY != movY);
        updateFalling(movY);

        if (oldMovX != movX) {//collided in the x axis
            velocity.x = 0.0;
        }

        if (oldMovZ != movZ) {//collided in the z axis
            velocity.z = 0.0;
        }
    }

    public void move() {
        move(velocity.x, velocity.y, velocity.z);
    }

    public void rotate(float pitch, float yaw) {
        final Location loc = getLocation();
        loc.setYaw(loc.getYaw() + yaw);
        loc.setPitch(loc.getPitch() + pitch);
        setLocation(loc);
    }

    public void setRelativeVelocity(float strafe, float up, float forward) {
        double radYaw = Math.toRadians(location.getYaw());
        double sinYaw = Math.sin(radYaw);
        double cosYaw = Math.cos(radYaw);
        velocity.set(
                strafe * sinYaw - forward * cosYaw,
                up,
                forward * sinYaw + strafe * cosYaw
        );
    }

    /**
     * Called every game tick
     */
    public void onTick() {
    }
}
