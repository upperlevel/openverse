package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.event.EventManager;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.PacketSide;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.game.ClientScene;
import xyz.upperlevel.openverse.client.render.inventory.GuiManager;
import xyz.upperlevel.openverse.client.render.inventory.InventoryGuiRegistry;
import xyz.upperlevel.openverse.client.render.inventory.ItemRendererRegistry;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.console.log.OpenverseLogger;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.ulge.game.Stage;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.key.Key;

import java.io.PrintStream;
import java.util.logging.Logger;

import static xyz.upperlevel.openverse.Openverse.PROTOCOL;

@Getter
public class OpenverseClient implements OpenverseProxy {
    private static OpenverseClient instance;

    private final Logger logger;

    private final Client endpoint;
    private final Channel channel;
    private final ClientResources resources; // resources are loaded per universe
    private final EventManager eventManager = new EventManager();
    private final EntityManager entityManager;
    private final ItemRendererRegistry itemRendererRegistry;
    private final InventoryGuiRegistry inventoryGuiRegistry;
    private final GuiManager guiManager;

    @Getter
    private boolean captureInput = true;
    @Setter
    private Player player;

    public OpenverseClient(@NonNull Client client, PrintStream writer) {
        instance = this;
        Openverse.setProxy(this);

        endpoint = client;

        logger = new OpenverseLogger(this, "Client", writer);

        Connection connection = client.getConnection();
        channel = new Channel("main").setProtocol(PROTOCOL.compile(PacketSide.CLIENT));
        connection.setDefaultChannel(channel);
        resources = new ClientResources(logger);
        resources.init();
        entityManager = new EntityManager();
        itemRendererRegistry = new ItemRendererRegistry();
        inventoryGuiRegistry = new InventoryGuiRegistry();
        guiManager = new GuiManager();
    }

    public void onTick() {
        entityManager.onTick();
    }

    /**
     * This method is called from the launcher to initialize the {@link OpenverseClient}.
     * It will setup the client scene for the given stage.
     *
     * @param stage the stage to use
     */
    public void join(Stage stage) {
        stage.setScene(new ClientScene());
    }

    public void setCaptureInput(boolean enable) {
        OpenverseLauncher.get().getGame().getWindow().setCursorEnabled(!enable);
        captureInput = enable;
    }

    public boolean isShifting() {
        Window w = OpenverseLauncher.get().getGame().getWindow();
        return w.testKey(Key.LEFT_SHIFT) || w.testKey(Key.RIGHT_SHIFT);
    }

    @Override
    public ClientResources getResources() {
        return resources;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static OpenverseClient get() {
        return instance;
    }
}
