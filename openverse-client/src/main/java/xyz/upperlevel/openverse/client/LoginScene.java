package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.network.LoginRequestPacket;
import xyz.upperlevel.openverse.network.LoginResponsePacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.game.Scene;

/**
 * During this scene the client sends a {@link LoginRequestPacket} to the server
 * and it responses back with a {@link LoginResponsePacket}.
 */
@Getter
@RequiredArgsConstructor
public class LoginScene implements Scene, Listener {
    private final OpenverseClient client;

    @Override
    public void onEnable(Scene previous) {
        //client.getChannel().register(this);
        client.getEndpoint().getConnection().send(client.getChannel(), new LoginRequestPacket());
    }

    @Override
    public void onDisable(Scene next) {
        // todo unregister listener on disable
    }

    @Override
    public void onRender() {
        GL11.glClearColor(0, 1f, 0, 0);
    }

    @EventHandler
    public void onLoginResponse(LoginResponsePacket packet) {
        // todo init player
        // todo change scene to GameScene
    }
}
