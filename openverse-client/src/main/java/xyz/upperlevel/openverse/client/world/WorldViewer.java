package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.Launcher;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.network.inventory.InventoryContentPacket;
import xyz.upperlevel.openverse.network.inventory.PlayerCloseInventoryPacket;
import xyz.upperlevel.openverse.network.inventory.PlayerOpenInventoryPacket;
import xyz.upperlevel.openverse.network.inventory.SlotChangePacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangePositionPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeWorldPacket;
import xyz.upperlevel.openverse.util.exceptions.NotImplementedException;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;
import xyz.upperlevel.ulge.util.math.CameraUtil;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.ResizeEvent;

/**
 * This class represents the player.
 * <p>Better, it represents the camera moving around the world and manages rendering stuff.
 */
public class WorldViewer implements PacketListener, Listener {
    @Getter
    private final OpenverseClient client;

    @Getter
    private final WorldSession worldSession;

    @Getter
    @Setter
    private LivingEntity entity;

    @Getter
    @Setter
    private Program program;
    private Uniform cameraLoc;
    private float aspectRatio = 1f;

    public WorldViewer(OpenverseClient client, LivingEntity entity) {
        this.client = client;

        this.entity = entity;
        this.program = client.getResources().programs().entry("simple_shader");
        this.worldSession = new WorldSession(client, program);
        this.cameraLoc = program.getUniform("camera");
        Window window = Launcher.get().getGame().getWindow();
        window.getEventManager().register(this);
        reloadAspectRatio();
    }

    /**
     * Starts listening for server packets.
     */
    public void listen() {
        client.getChannel().register(this);
    }

    public void render(float partialTicks) {
        program.use();
        Location loc = entity.getEyePosition(partialTicks);

        cameraLoc.set(CameraUtil.getCamera(
                45f,
                aspectRatio,
                0.01f,
                10000f,
                (float) Math.toRadians(loc.getYaw()),
                (float) Math.toRadians(loc.getPitch()),
                (float) loc.getX(),
                (float) loc.getY(),
                (float) loc.getZ()
        ));

        worldSession.getChunkView().render(program);
    }

    public void reloadAspectRatio() {
        Window window = Launcher.get().getGame().getWindow();
        aspectRatio = window.getWidth() / (float) window.getHeight();
    }

    @EventHandler
    public void onWindowResize(ResizeEvent event) {
        reloadAspectRatio();
    }

    @PacketHandler
    public void onPlayerChangeWorld(Connection conn, PlayerChangeWorldPacket pkt) {
        worldSession.setWorld(new ClientWorld(client, pkt.getWorldName()));
        client.getLogger().info("Viewer changed world to: " + pkt.getWorldName());
    }

    @PacketHandler
    public void onPlayerChangePosition(Connection conn, PlayerChangePositionPacket pkt) {
        //TODO update player pos
        client.getLogger().info("Viewer changed position to: " + pkt.getX() + " " + pkt.getY() + " " + pkt.getZ());
    }

    @PacketHandler
    public void onPlayerChangeLook(Connection conn, PlayerChangeLookPacket pkt) {
        //TODO update player look
        client.getLogger().info("Viewer changed position to: " + pkt.getYaw() + " " + pkt.getPitch());
    }

    @PacketHandler
    public void onSlotChange(Connection conn, SlotChangePacket packet) {
        Player player = OpenverseClient.get().getPlayer();
        if (packet.getInventoryId() == 0) {//Player inventory is always 0
            PlayerInventory inventory = player.getInventory();
            inventory.get(packet.getSlotId()).swap(packet.getNewItem());
        } else throw new NotImplementedException();
        client.getLogger().info("slot change received");
        //TODO update multi-inventory view and graphic things
    }

    @PacketHandler
    public void onInventoryContent(Connection conn, InventoryContentPacket packet) {
        Player player = OpenverseClient.get().getPlayer();
        if (packet.getInventoryId() == 0) {//Player inventory is always 0
            PlayerInventory inventory = player.getInventory();
            packet.apply(inventory);
        } else throw new NotImplementedException();
        client.getLogger().info("Inventory content received");
        //TODO update multi-inventory view and graphic things
    }


    @PacketHandler
    public void onPlayerOpenInventory(Connection conn, PlayerOpenInventoryPacket packet) {
        Player player = OpenverseClient.get().getPlayer();

        // Player#openInventory() is seen as an input so it sends the packet while
        // Player#openInventory(Inventory) does not
        player.openInventory(player.getInventory());
    }

    @PacketHandler
    public void onPlayerCloseInventory(Connection conn, PlayerCloseInventoryPacket packet) {
        Player player = OpenverseClient.get().getPlayer();
        player.onCloseInventory();
    }
}
