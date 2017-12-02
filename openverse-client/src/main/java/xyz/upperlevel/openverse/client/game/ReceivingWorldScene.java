package xyz.upperlevel.openverse.client.game;

import lombok.RequiredArgsConstructor;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.client.world.KeyboardInputEntityDriver;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeWorldPacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.ulge.game.Scene;

import static xyz.upperlevel.openverse.Openverse.channel;

@RequiredArgsConstructor
public class ReceivingWorldScene implements Scene, PacketListener {
    private final GameScene gameScene;

    @Override
    public void onEnable(Scene scene) {
        channel().register(this);
        Openverse.logger().info("Waiting for world...");
    }

    @Override
    public void onDisable(Scene scene) {
        channel().unregister(this);
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onFps() {
    }

    @Override
    public void onRender() {
        // Downloading the world...
        GL11.glClearColor(0, 1, 0, 0);
    }

    @PacketHandler
    public void onPlayerChangeWorld(Connection conn, PlayerChangeWorldPacket pkt) {
        ClientWorld w = new ClientWorld(pkt.getWorldName());
        Player pl = new Player(new Location(w, 0, 0, 0), "Maurizio"); // TODO add real player
        pl.setDriver(new KeyboardInputEntityDriver(gameScene.getWindow()));
        Openverse.entities().register(pl);
        gameScene.setScene(new PlayingWorldScene(pl));
        Openverse.logger().info("Received world, now you can play!");
    }
}
