package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.client.world.entity.input.PlayerEntityInput;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;
import xyz.upperlevel.ulge.opengl.buffer.Vao;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;

import static org.lwjgl.opengl.GL11.*;

// todo put in openverse launcher
@Getter
public class GameScene extends Stage implements Listener {
    private final ClientScene parent;
    private Window window;

    public GameScene(ClientScene parent) {
        this.parent = parent;
        window = OpenverseLauncher.get().getGame().getWindow();
        window.getEventManager().register(this);
        window.disableCursor();
    }

    @Override
    public void onEnable(Scene previous) {
        Openverse.logger().info("Listening for world packets...");
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        //window.setVSync(false);

        setScene(new ReceivingWorldScene(this));
    }

    @Override
    public void onRender() {
        getScene().onRender();
    }

    private void processInput() {
    }

    @Override
    public void onDisable(Scene next) {
        getScene().onDisable(next);
        Openverse.getEventManager().call(new ShutdownEvent());
    }

    @Override
    public void onTick() {
        OpenverseClient.get().onTick();
        getScene().onTick();
    }

    @Override
    public void onFps() {
        getScene().onFps();
    }

    @EventHandler
    public void onKeyChange(KeyChangeEvent e) {
        if (e.getAction() == Action.PRESS) {
            switch (e.getKey()) {
                case ESCAPE:
                    OpenverseLauncher.get().getGame().stop();
                    break;
            }
        }
    }
}
