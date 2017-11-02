package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangePositionPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeWorldPacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;
import xyz.upperlevel.ulge.util.math.CameraUtil;

import static xyz.upperlevel.openverse.util.math.MathUtil.lerp;

/**
 * This class represents the player.
 * <p>Better, it represents the camera moving around the world and manages rendering stuff.
 */
@Getter
@Setter
public class WorldViewer implements PacketListener {
    private final WorldSession worldSession;

    @Getter
    @Setter
    private LivingEntity entity;

    private Program program;
    private Uniform cameraLoc;
    private Matrix4f camera;

    public WorldViewer(LivingEntity entity) {
        this.entity = entity;
        this.program = ((ClientResources) Openverse.resources()).programs().entry("simple_shader");
        this.worldSession = new WorldSession(program);
        this.cameraLoc = program.uniformer.get("camera");
    }

    /**
     * Starts listening for server packets.
     */
    public void listen() {
        Openverse.channel().register(this);
    }

    public void render(float partialTicks) {
        program.bind();
        Location loc = entity.getEyePosition(partialTicks);

        program.uniformer.setUniformMatrix4("camera", CameraUtil.getCamera(
                45f,
                1f,
                0.01f,
                10000f,
                (float) Math.toRadians(loc.getYaw()),
                (float) Math.toRadians(loc.getPitch()),
                (float) loc.getX(),
                (float) loc.getY(),
                (float) loc.getZ()
        ).get(BufferUtils.createFloatBuffer(16)));

        worldSession.getChunkView().render(program);
    }

    @PacketHandler
    public void onPlayerChangeWorld(Connection conn, PlayerChangeWorldPacket pkt) {
        worldSession.setWorld(new ClientWorld(pkt.getWorldName()));
        //Openverse.logger().info("Viewer changed world to: " + pkt.getWorldName());
    }

    @PacketHandler
    public void onPlayerChangePosition(Connection conn, PlayerChangePositionPacket pkt) {
        //TODO update player pos
        //Openverse.logger().info("Viewer changed position to: " + pkt.getX() + " " + pkt.getY() + " " + pkt.getZ());
    }

    @PacketHandler
    public void onPlayerChangeLook(Connection conn, PlayerChangeLookPacket pkt) {
        //TODO update player look
        //Openverse.logger().info("Viewer changed position to: " + pkt.getYaw() + " " + pkt.getPitch());
    }
}
