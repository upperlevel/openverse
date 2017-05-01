package xyz.upperlevel.openverse.client.world.entity;

import lombok.Getter;
import org.joml.Vector3f;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.math.CameraUtil;

public class ClientPlayer extends ClientEntity implements Player {

    @Getter
    private final String name; // todo is the player able to change the name?

    @Getter
    private final Connection connection;

    public ClientPlayer(String name, Connection connection) {
        super(TYPE);
        this.name = name;
        this.connection = connection;
    }

    @Override
    public void render(Uniformer uniformer) {
        ClientWorld world = getWorld();
        if (world == null)
            return;
        world.render(uniformer);
    }
}
