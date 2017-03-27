package xyz.upperlevel.opencraft.client.view;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.physic.PhysicEngine;
import xyz.upperlevel.opencraft.common.world.Location;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.math.CameraUtil;
import xyz.upperlevel.ulge.window.Window;

public class WorldViewer extends ControllableEntity {

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
    protected WorldView world = new WorldView();

    public WorldViewer() {
    }

    private Matrix4f camera = null;

    public void updateCamera() {
        Location loc = getLoc();
        camera = CameraUtil.getCamera(
                45f,
                1f,
                0.01f,
                100000f,
                (float) Math.toRadians(loc.getYaw()),
                (float) Math.toRadians(loc.getPitch()),
                loc.getX(),
                loc.getY(),
                loc.getZ()
        );
    }

    private int chunkX, chunkY, chunkZ;

    public void updateView() { // updates position and send changes
        Location loc = getLoc();

        int acx = (int) Math.floor(loc.getX() / 16f);
        int acy = (int) Math.floor(loc.getY() / 16f);
        int acz = (int) Math.floor(loc.getZ() / 16f);

        if (chunkX != acx || chunkY != acy || chunkZ != acz)
            world.setCenter(acx, acy, acz);

        chunkX = acx;
        chunkY = acy;
        chunkZ = acz;
    }

    public void draw(Window w, Uniformer uniformer) {
        updateCamera();
        updateView();

        // todo temporary here
        PhysicEngine.in.update(w, this, world, 0);

        System.out.println(getLoc().getY());

        if (camera != null) {
            uniformer.setUniformMatrix4("cam", camera.get(BufferUtils.createFloatBuffer(16)));
            world.draw(uniformer);
        }
    }
}
