package xyz.upperlevel.openverse.client;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.client.world.entity.input.PlayerEntityInput;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.opengl.buffer.Vao;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.KeyChangeEvent;
import xyz.upperlevel.ulge.window.event.action.Action;

import static org.lwjgl.opengl.GL11.*;

// todo put in openverse launcher
@Getter
public class GameScene implements Scene, Listener {
    private final ClientScene parent;

    private WorldViewer viewer;
    private Window window;
    private PlayerEntityInput input;

    public GameScene(ClientScene parent) {
        this.parent = parent;
        this.viewer = new WorldViewer();
        window = OpenverseLauncher.get().getGame().getWindow();
        window.getEventManager().register(this);
        window.disableCursor();
        input = new PlayerEntityInput(window);
    }

    @Override
    public void onEnable(Scene previous) {
        Openverse.logger().info("Listening for world packets...");
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        //window.setVSync(false);


        LivingEntity viewerEntity = new Player(new Location(null, 0, 0, 0), "Maurizio");//TODO add real player
        viewerEntity.setInput(input);
        viewer.setEntity(viewerEntity);

        viewer.listen();
        Openverse.entities().register(viewerEntity);
    }

    @Override
    public void onRender() {
        processInput();
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glClearColor(0.392f, 0.922f, 1f, 0);
        viewer.render();
    }

    private void processInput() {
    }

    @Override
    public void onDisable(Scene next) {
        Openverse.getEventManager().call(new ShutdownEvent());
    }

    @Override
    public void onTick() {
        OpenverseClient.get().onTick();
    }

    @Override
    public void onFps() {
        Openverse.logger().info("Fps: " + OpenverseLauncher.get().getGame().getFps() + " chunks: " + viewer.getWorldSession().getChunkView().getChunks().size() + ", vaos:" + Vao.instances);
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
