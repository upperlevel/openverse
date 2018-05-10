package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.EventManager;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.PacketSide;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.console.log.OpenverseLogger;
import xyz.upperlevel.openverse.event.ShutdownEvent;
import xyz.upperlevel.openverse.server.command.CommandInterpreter;
import xyz.upperlevel.openverse.server.command.CommandRegistry;
import xyz.upperlevel.openverse.server.command.CommandSender;
import xyz.upperlevel.openverse.server.command.DefaultCommandInterpreter;
import xyz.upperlevel.openverse.server.inventory.InventoryManager;
import xyz.upperlevel.openverse.server.resource.ServerResources;
import xyz.upperlevel.openverse.server.world.Universe;
import xyz.upperlevel.openverse.world.entity.EntityManager;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Logger;

import static xyz.upperlevel.openverse.Openverse.resources;

@Getter
public class OpenverseServer implements OpenverseProxy, Listener {
    private final Logger logger;
    private final Server endpoint;
    private Channel channel;
    private Universe universe;
    private final EventManager eventManager = new EventManager();
    private final ServerResources resources;
    private final PlayerManager playerManager;
    private final EntityManager entityManager;
    private final InventoryManager inventoryManager;
    private final CommandRegistry commandRegistry;
    private CommandInterpreter commandInterpreter;

    public OpenverseServer(@NonNull Server server, PrintStream writer) {
        Openverse.setProxy(this);
        logger = new OpenverseLogger(this, "Server", writer);
        this.endpoint = server;
        this.channel = new Channel("main").setProtocol(Openverse.PROTOCOL.compile(PacketSide.SERVER));
        endpoint.setDefaultChannel(channel);
        this.commandInterpreter = new DefaultCommandInterpreter();
        this.universe = new Universe();
        this.playerManager = new PlayerManager();
        this.entityManager = new EntityManager();
        this.inventoryManager = new InventoryManager();
        this.commandRegistry = new CommandRegistry();
        this.resources = new ServerResources(new File("server/resources"), logger);
        resources.init();
    }

    /**
     * This scene is called from launchers to init {@link OpenverseServer}.
     * It will just load resources and other saves (including world).
     */
    public void join() {
        long init = System.currentTimeMillis();
        Openverse.getLogger().info("Loading resources...");
        resources().setup();
        resources().load();
        Openverse.getLogger().info("Resources loaded in " + (System.currentTimeMillis() - init) + " ms.");

        Openverse.getLogger().info("Listening for incoming connections...");
        playerManager.start();
    }

    public void onTick() {//TODO: call
        entityManager.onTick();
        universe.onTick();
    }

    public void executeCommand(String line) {
        commandInterpreter.process(CommandSender.CONSOLE, commandRegistry, line);
    }

    public int tabComplete(String line, List<String> completions) {
        return commandInterpreter.tabComplete(CommandSender.CONSOLE, commandRegistry, line, completions);
    }

    public void stop() {
        Openverse.getEventManager().call(new ShutdownEvent());
    }

    public static OpenverseServer get() {
        return (OpenverseServer) Openverse.getProxy();
    }
}
