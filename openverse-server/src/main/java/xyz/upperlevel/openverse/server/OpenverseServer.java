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
import xyz.upperlevel.openverse.server.command.*;
import xyz.upperlevel.openverse.server.inventory.InventoryManager;
import xyz.upperlevel.openverse.server.resource.ServerResources;
import xyz.upperlevel.openverse.server.world.Universe;
import xyz.upperlevel.openverse.world.entity.EntityManager;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Logger;

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

    private final CommandInterpreter commandInterpreter;
    private final CommandRegistry commandRegistry;
    private final CommandSender consoleCommandSender;

    public OpenverseServer(@NonNull Server server, PrintStream writer) {
        this.logger = new OpenverseLogger(this, "Server", writer);
        this.logger.info("Hey! This is the server... :)");

        this.endpoint = server;
        this.channel = new Channel("main").setProtocol(Openverse.PROTOCOL.compile(PacketSide.SERVER));
        endpoint.setDefaultChannel(channel);
        this.universe = new Universe(this);
        this.playerManager = new PlayerManager(this);
        this.entityManager = new EntityManager(this);
        this.inventoryManager = new InventoryManager(this);

        this.commandInterpreter = new DefaultCommandInterpreter();
        this.commandRegistry = new CommandRegistry(this);
        this.consoleCommandSender = new ConsoleCommandSender(logger);

        this.resources = new ServerResources(this, new File("server/resources"), logger);
        resources.init();


    }

    /**
     * This scene is called from launchers to init {@link OpenverseServer}.
     * It will just load resources and other saves (including world).
     */
    public void join() {
        System.out.println("WHY SERVER IS JOINING :(");
        long init = System.currentTimeMillis();
        logger.info("Loading resources...");
        resources.setup();
        resources.load();
        logger.info("Resources loaded in " + (System.currentTimeMillis() - init) + " ms.");

        logger.info("Listening for incoming connections...");
        playerManager.start();
    }

    public void onTick() {//TODO: call
        entityManager.onTick();
        universe.onTick();
    }

    public void executeCommand(String line) {
        commandInterpreter.process(consoleCommandSender, commandRegistry, line);
    }

    public int tabComplete(String line, List<String> completions) {
        return commandInterpreter.tabComplete(consoleCommandSender, commandRegistry, line, completions);
    }

    public void stop() {
        eventManager.call(new ShutdownEvent());
    }
}
