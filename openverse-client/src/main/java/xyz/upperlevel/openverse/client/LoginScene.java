package xyz.upperlevel.openverse.client;

import lombok.Getter;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.hermes.client.impl.direct.DirectClientConnection;
import xyz.upperlevel.hermes.impl.direct.DirectConnection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.LoginRequestPacket;
import xyz.upperlevel.openverse.network.LoginResponsePacket;
import xyz.upperlevel.ulge.game.Scene;

/**
 * During this scene the client sends a {@link LoginRequestPacket} to the server
 * and it responses back with a {@link LoginResponsePacket}.
 */
@Getter
public class LoginScene implements Scene, PacketListener {
    private final ClientScene parent;

    public LoginScene(ClientScene parent) {
        this.parent = parent;
    }

    @Override
    public void onEnable(Scene previous) {
        Openverse.channel().register(this);
        System.out.println("OTHER: " + ((DirectClientConnection)OpenverseClient.get().getEndpoint().getConnection()).getOther().getDefaultChannel());
        OpenverseClient.get().getEndpoint().getConnection().send(OpenverseClient.get().getChannel(), new LoginRequestPacket());
    }

    @Override
    public void onDisable(Scene next) {
        // todo unregister packet listener
    }

    @Override
    public void onRender() {
        GL11.glClearColor(0, 1f, 0, 0);
    }

    @PacketHandler
    public void onLoginResponse(Connection conn, LoginResponsePacket pkt) {
        // todo init player
        parent.setScene(new GameScene(parent));
    }
}
