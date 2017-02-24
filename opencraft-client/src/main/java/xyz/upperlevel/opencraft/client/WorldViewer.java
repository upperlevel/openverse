package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.controller.ControllableEntity;
import xyz.upperlevel.opencraft.common.network.SingleplayerClient;
import xyz.upperlevel.opencraft.client.render.RenderArea;
import xyz.upperlevel.opencraft.common.network.packet.AskChunkPacket;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.opencraft.common.network.SingleplayerServer;
import xyz.upperlevel.opencraft.common.world.CommonChunk;
import xyz.upperlevel.ulge.util.math.CameraUtil;

public class WorldViewer implements ControllableEntity {

    @Getter
    private double x, y, z;

    @Getter
    private float yaw, pitch;

    @Getter
    private RenderArea renderArea = new RenderArea();

    public WorldViewer() {
    }

    public void sendPositionChanges() {
        SingleplayerServer.connection().sendPacket(new PlayerTeleportPacket(x, y, z, yaw, pitch));
    }

    public int getChunkX() {
        return (int) x / CommonChunk.WIDTH;
    }

    public int getChunkY() {
        return (int) y / CommonChunk.HEIGHT;
    }

    public int getChunkZ() {
        return (int) z / CommonChunk.LENGTH;
    }

    @Override
    public void setX(double x) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setY(double y) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setZ(double z) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setYaw(float yaw) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setPitch(float pitch) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void teleport(float yaw, float pitch) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void teleport(double x, double y, double z) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void teleport(double x, double y, double z, float yaw, float pitch) {
        teleport(x, y, z, yaw, pitch, true);
    }

    public void teleport(double x, double y, double z, float yaw, float pitch, boolean sendPacket) {
        // stores last chunk xyz
        int lcx = getChunkX();
        int lcy = getChunkY();
        int lcz = getChunkZ();

        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        if (sendPacket)
            sendPositionChanges();

        // gets actual chunk xyz
        int acx = getChunkX();
        int acy = getChunkY();
        int acz = getChunkZ();

        if (lcx != acx || lcy != acy || lcz != acz)
            SingleplayerClient.connection().sendPacket(new AskChunkPacket(acx, acy, acz));

        renderArea.destroy();
    }

    public void draw() {
        Matrix4f mat;
        mat = CameraUtil.getCamera(
                90f,
                1f / 2f,
                0.01f,
                100f,
                (float) Math.toRadians(yaw),
                (float) Math.toRadians(pitch),
                getChunkX(),
                getChunkY(),
                getChunkZ()
        );
        Client.programUni.setUniformMatrix4("cam", mat.get(BufferUtils.createFloatBuffer(16)));
        renderArea.draw();
    }
}