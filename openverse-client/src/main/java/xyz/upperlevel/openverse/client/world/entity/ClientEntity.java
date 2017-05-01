package xyz.upperlevel.openverse.client.world.entity;

import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.BaseEntity;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

public abstract class ClientEntity extends BaseEntity {

    public ClientEntity(EntityType type) {
        super(type, null);
    }

    @Override
    public ClientWorld getWorld() {
        Location location = getLocation();
        return location == null ? null : (ClientWorld) location.getWorld();
    }

    public abstract void render(Uniformer uniformer);
}
