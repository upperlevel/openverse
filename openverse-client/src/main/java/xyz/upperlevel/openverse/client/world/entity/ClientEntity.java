package xyz.upperlevel.openverse.client.world.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.EntityLocation;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

public abstract class ClientEntity implements Entity {

    @Getter
    @Setter
    private int id = -1;

    @Getter
    @NonNull
    private final EntityType type;

    @Getter
    @NonNull
    private final EntityLocation<ClientWorld> location;

    @Getter
    @NonNull
    private Vector3f velocity = new Vector3f();

    public ClientEntity(EntityType type, EntityLocation<ClientWorld> location) {
        this.type =  type;
        this.location = location;
    }

    @Override
    public ClientWorld getWorld() {
        return location.getWorld();
    }

    @Override
    public void setLocation(Location location) {
        this.location.set(location);
    }

    @Override
    public void setVelocity(Vector3f velocity) {
        this.velocity.set(velocity);
    }

    public abstract void render(Uniformer uniformer);
}
