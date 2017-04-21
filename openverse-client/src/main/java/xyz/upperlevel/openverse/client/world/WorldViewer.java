package xyz.upperlevel.openverse.client.world;


import lombok.Getter;
import lombok.Setter;

public class WorldViewer {

    @Getter
    @Setter
    public double x, y, z;

    @Getter
    @Setter
    public double yaw, pitch;

    public WorldViewer() {
    }

    public WorldViewer(double x, double y, double z) {
        setPosition(x, y, z);
    }

    public WorldViewer(double yaw, double pitch) {
        setRotation(yaw, pitch);
    }

    public WorldViewer(double x, double y, double z, double yaw, double pitch) {
        setPosition(x, y, z, yaw, pitch);
    }

    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(double yaw, double pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setPosition(double x, double y, double z, double yaw, double pitch) {
        setPosition(x, y, z);
        setRotation(yaw, pitch);
    }
}
