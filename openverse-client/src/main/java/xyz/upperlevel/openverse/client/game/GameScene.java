package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.Launcher;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;

import static org.lwjgl.opengl.GL11.*;

public class GameScene extends Stage implements Listener {
    @Getter
    private final OpenverseClient client;

    @Getter
    private final ClientScene parent;

    @Getter
    private Game game;

    @Getter
    private Window window;

    public GameScene(OpenverseClient client, ClientScene parent) {
        this.client = client;

        this.parent = parent;
        game = Launcher.get().getGame();
        window = game.getWindow();
        window.getEventManager().register(this);
        window.setCursorEnabled(false);
    }

    @Override
    public void onEnable(Scene previous) {
        client.getLogger().info("Listening for world packets...");
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        window.setVSync(false);

        setScene(new ReceivingWorldScene(client, this));
    }

    @Override
    public void onRender() {
        getScene().onRender();
    }

    @Override
    public void onDisable(Scene next) {
        getScene().onDisable(next);
        client.getEventManager().call(new ShutdownEvent());
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
        if (!OpenverseClient.get().isCaptureInput()) return;
        if (e.getAction() == Action.PRESS) {
            switch (e.getKey()) {
                case ESCAPE:
                    Launcher.get().getGame().stop();
                    break;
            }
        }
    }
}
