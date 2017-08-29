package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.network.world.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangePositionPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangeWorldPacket;
import xyz.upperlevel.ulge.opengl.shader.Program;

/**
 * This class represents the player.
 * Better, it represents the camera moving around the world and manages rendering stuff.
 */
@Getter
@Setter
public class WorldViewer implements PacketListener {
    private final WorldSession worldSession;

    private float x, y, z, yaw, pitch;
    private int distance;

    private Program program;

    public WorldViewer() {
        this.worldSession = new WorldSession();
        this.program = ((ClientResources) Openverse.resources()).programs().entry("simple_shader");
        Openverse.channel().register(this);
    }

    /**
     * Sets position unsafely: it doesn't send any packet to the server.
     */
    public void unsafeSetPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        // todo update world session
    }

    public void setPosition(PlayerChangePositionPacket pkt) {
        x = pkt.getX();
        y = pkt.getY();
        z = pkt.getZ();
    }

    public void setPosition(float x, float y, float z) {
        unsafeSetPosition(x, y, z);
        // todo send packet
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        setPosition(x + offsetX, y + offsetY, z + offsetZ);
    }

    public void forward(float speed) {

    }

    public void right(float speed) {

    }

    public void up(float speed) {

    }

    /**
     * Sets look unsafely: it doesn't send any packet to the server.
     */
    public void unsafeSetLook(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setLook(PlayerChangeLookPacket pkt) {
        yaw = pkt.getYaw();
        pitch = pkt.getPitch();
    }

    public void setLook(float yaw, float pitch) {
        unsafeSetLook(yaw, pitch);
        // todo send packet
    }

    public void rotateLook(float offsetYaw, float offsetPitch) {
        setLook(yaw + offsetYaw, pitch + offsetPitch);
    }

    /**
     * Firstly binds the program and then renders all chunks.
     */
    public void render() {
        program.bind();
        for (int ix = -distance; ix < distance; ix++) {
            for (int iy = -distance; iy < distance; iy++) {
                for (int iz = -distance; iz < distance; iz++) {
                    worldSession.getChunkView().getChunk(ix, iy, iz).render(program);
                }
            }
        }
        worldSession.getEntityView().render();
        // todo render player arms
    }

    @PacketHandler
    public void onPlayerChangeWorld(Connection conn, PlayerChangeWorldPacket pkt) {
        worldSession.setWorld(new ClientWorld(pkt.getWorldName()));
    }

    @PacketHandler
    public void onPlayerChangePosition(Connection conn, PlayerChangePositionPacket pkt) {
        setPosition(pkt);
    }
    
    @PacketHandler
    public void onPlayerChangeLook(Connection conn, PlayerChangeLookPacket pkt) {
        setLook(pkt);
    }
}
