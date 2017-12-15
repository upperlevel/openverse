package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;

import static org.lwjgl.opengl.GL11.*;

// todo put in openverse launcher
@Getter
public class GameScene extends Stage implements Listener {
    private final ClientScene parent;
    private Game game;
    private Window window;

    public GameScene(ClientScene parent) {
        this.parent = parent;
        game = OpenverseLauncher.get().getGame();
        window = game.getWindow();
        window.getEventManager().register(this);
        window.setCursorEnabled(false);
    }

    @Override
    public void onEnable(Scene previous) {
        Openverse.getLogger().info("Listening for world packets...");
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        window.setVSync(false);

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
        System.exit(0);//Damn it jline, really?
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
