package xyz.upperlevel.openverse.client.world.entity;

import lombok.Getter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

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
        world.render(uniformer); // renders the world

        // todo render player shape
    }
}
