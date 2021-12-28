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
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.ulge.game.Stage;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.key.Key;

import java.io.PrintStream;
import java.util.logging.Logger;

import static xyz.upperlevel.openverse.Openverse.PROTOCOL;

public class OpenverseClient implements OpenverseProxy {
    private static OpenverseClient instance;

    private final Logger logger;
    private final Client endpoint;
    private final Channel channel;
    private final ClientResources resources; // resources are loaded per universe
    private final EventManager eventManager;
    private final EntityManager entityManager;

    @Getter
    private final ItemRendererRegistry itemRendererRegistry;

    @Getter
    private final InventoryGuiRegistry inventoryGuiRegistry;

    @Getter
    private final GuiManager guiManager;

    //@Getter
    //private final DebugGuiManager debugGuiManager;

    @Getter
    private boolean captureInput = true;

    @Getter
    @Setter
    private Player player;

    public OpenverseClient(Client client, PrintStream writer) {
        instance = this;

        this.eventManager = new EventManager();

        this.logger = new OpenverseLogger(this, "Client", writer);

        this.endpoint = client;

        Connection connection = client.getConnection();
        this.channel = new Channel("main").setProtocol(PROTOCOL.compile(PacketSide.CLIENT));
        connection.setDefaultChannel(channel);

        this.resources = new ClientResources(this, logger);
        this.resources.init();
        this.entityManager = new EntityManager(this);
        this.itemRendererRegistry = new ItemRendererRegistry();
        this.inventoryGuiRegistry = new InventoryGuiRegistry();
        this.guiManager = new GuiManager(this);
        //this.debugGuiManager = new DebugGuiManager(Launcher.get().getGame().getWindow().getId());
    }

    public void onTick() {
        entityManager.onTick();
        player.getWorld().onTick();
    }

    /**
     * This method is called from the launcher to initialize the {@link OpenverseClient}.
     * It will setup the client scene for the given stage.
     *
     * @param stage the stage to use
     */
    public void join(Stage stage) {
        System.out.println("YUP I'M JOINING CORRECTLY!");
        stage.setScene(new ClientScene(this));
    }

    public void setCaptureInput(boolean enable) {
        Launcher.get().getGame().getWindow().setCursorEnabled(!enable);
        captureInput = enable;
    }

    public boolean isShifting() {
        Window w = Launcher.get().getGame().getWindow();
        return w.testKey(Key.LEFT_SHIFT) || w.testKey(Key.RIGHT_SHIFT);
    }

    @Override
    public EventManager getEventManager() {
        return this.eventManager;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public ClientResources getResources() {
        return this.resources;
    }

    @Override
    public Client getEndpoint() {
        return this.endpoint;
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static OpenverseClient get() {
        return instance;
    }
}
