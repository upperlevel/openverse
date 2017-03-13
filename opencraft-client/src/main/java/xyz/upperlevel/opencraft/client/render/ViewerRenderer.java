package xyz.upperlevel.opencraft.client.render;

import jdk.nashorn.internal.ir.Block;
import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.physic.PhysicalViewer;
import xyz.upperlevel.opencraft.client.physic.impl.GravitySupplier;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.math.CameraUtil;

public class ViewerRenderer extends PhysicalViewer {

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
    protected ViewRenderer view = new ViewRenderer(this);

    public ViewerRenderer() {
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
            view.setCenter(acx, acy, acz);

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

    private long lastTime = -1;



    public BlockRenderer getBlock() {
        int vx = (int) view.getViewX(x);
        int vy = (int) view.getViewY(y);
        int vz = (int) view.getViewZ(z);

        return view.getBlock(vx, vy, vz);
    }

    public void draw(Uniformer uniformer) {
        if (lastTime == -1)
            lastTime = System.currentTimeMillis();
        long currTime = System.currentTimeMillis();
        GravitySupplier.DEVO_FUNZIONARE_SE_NO_JAVA_NON_E$_UN_GRAN_LINGUAGGIO.update(this, currTime - lastTime);
        lastTime = currTime;

        if (camera != null) {
            uniformer.setUniformMatrix4("cam", camera.get(BufferUtils.createFloatBuffer(16)));
            view.draw(uniformer);
        }
    }
}
