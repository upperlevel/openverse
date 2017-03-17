package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.physic.PhysicEngine;
import xyz.upperlevel.opencraft.client.physic.PhysicalViewer;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.math.CameraUtil;

public class WorldViewer extends PhysicalViewer {

    @Getter
    @Setter
    protected float fov;

    @Getter
    @Setter
    protected float aspectRatio;

    @Getter
    @Setter
    protected float nearPane, farPane;

    @Getter
    @Setter
    protected LocalWorld world = new LocalWorld();

    public WorldViewer() {
    }

    private Matrix4f camera = null;

    public void updateCamera() {
        camera = CameraUtil.getCamera(
                45f,
                1f,
                0.01f,
                100000f,
                (float) Math.toRadians(yaw),
                (float) Math.toRadians(pitch),
                x * 2f - 1f,
                y * 2f - 1f,
                z * 2f + 1f
        );
    }

    private int chunkX, chunkY, chunkZ;

    public void updateView() { // updates position and send changes
        float rx = x / 16f;
        float ry = y / 16f;
        float rz = z / 16f;

        int acx = (int) Math.floor(rx);
        int acy = (int) Math.floor(ry);
        int acz = (int) Math.ceil(rz);

        if (chunkX != acx || chunkY != acy || chunkZ != acz)
            world.setCenter(acx, acy, acz);

        chunkX = acx;
        chunkY = acy;
        chunkZ = acz;
    }

    @Override
    public void updatePosition() {
        updateCamera();
        updateView();
    }

    @Override
    public void updateRotation() {
        updateCamera();
    }


    public void draw(Uniformer uniformer) {
        if (camera != null) {
            uniformer.setUniformMatrix4("cam", camera.get(BufferUtils.createFloatBuffer(16)));
            world.draw(uniformer);
        }
    }
}
