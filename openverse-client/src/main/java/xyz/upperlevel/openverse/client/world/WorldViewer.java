package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.world.ChunkRenderer;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.network.world.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangePositionPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangeWorldPacket;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;
import xyz.upperlevel.ulge.util.math.CameraUtil;

/**
 * This class represents the player.
 * Better, it represents the camera moving around the world and manages rendering stuff.
 */
@Getter
@Setter
public class WorldViewer implements PacketListener {
    private final WorldSession worldSession;

    private double x, y, z, yaw, pitch;

    private Program program;
    private Uniform cameraLoc;
    private Matrix4f camera;

    public WorldViewer() {
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

    /**
     * Sets position unsafely: it doesn't send any packet to the server.
     */
    public void unsafeSetPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        camera = null;
    }

    public void setPosition(PlayerChangePositionPacket pkt) {
        unsafeSetPosition(pkt.getX(), pkt.getY(), pkt.getZ());
    }

    public void setPosition(double x, double y, double z) {
        unsafeSetPosition(x, y, z);
        ((Client) Openverse.endpoint()).getConnection().send(Openverse.channel(), new PlayerChangePositionPacket(x, y, z));
    }

    public void movePosition(double offsetX, double offsetY, double offsetZ) {
        setPosition(x + offsetX, y + offsetY, z + offsetZ);
    }

    private Matrix4f getOrientation() {
        return new Matrix4f().rotationXYZ((float)Math.toRadians(pitch), (float)Math.toRadians(yaw), 0.0f);
    }

    private Vector3f getForward() {
        return getOrientation().invertAffine().transformDirection(new Vector3f(0, 0, -1f));
    }

    public void forward(float speed) {
        Vector3f v = getForward().mul(speed);
        movePosition(v.x, v.y, v.z);
    }

    private Vector3f getRight() {
        return getOrientation().invertAffine().transformDirection(new Vector3f(1f, 0, 0));
    }

    public void right(float speed) {
        Vector3f v = getRight().mul(speed);
        movePosition(v.x, v.y, v.z);
    }

    private Vector3f getUp() {
        return new Vector3f(0, 1f, 0);
    }

    public void up(float speed) {
        Vector3f v = getUp().mul(speed);
        movePosition(v.x, v.y, v.z);
    }

    /**
     * Sets look unsafely: it doesn't send any packet to the server.
     */
    public void unsafeSetLook(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setLook(PlayerChangeLookPacket pkt) {
        yaw = pkt.getYaw();
        pitch = pkt.getPitch();
    }

    public void setLook(double yaw, double pitch) {
        if (pitch < -90.0)
            pitch = -90.0;
        else if (pitch > 90.0)
            pitch = 90.0;
        unsafeSetLook(yaw, pitch);
        ((Client) Openverse.endpoint()).getConnection().send(Openverse.channel(), new PlayerChangeLookPacket(yaw, pitch));
    }

    public void rotateLook(double offsetYaw, double offsetPitch) {
        setLook(yaw + offsetYaw, pitch + offsetPitch);
    }

    /**
     * Firstly binds the program and then renders all chunks.
     */
    public void render() {
        program.bind();
        program.uniformer.setUniformMatrix4("camera", CameraUtil.getCamera(
                45f,
                1f,
                0.01f,
                10000f,
                (float) Math.toRadians(yaw),
                (float) Math.toRadians(pitch),
                (float) x,
                (float) y,
                (float) z
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
        setPosition(pkt);
        //Openverse.logger().info("Viewer changed position to: " + pkt.getX() + " " + pkt.getY() + " " + pkt.getZ());
    }

    @PacketHandler
    public void onPlayerChangeLook(Connection conn, PlayerChangeLookPacket pkt) {
        setLook(pkt);
        //Openverse.logger().info("Viewer changed position to: " + pkt.getYaw() + " " + pkt.getPitch());
    }
}
