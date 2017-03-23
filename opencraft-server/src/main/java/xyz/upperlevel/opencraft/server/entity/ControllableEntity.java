package xyz.upperlevel.opencraft.server.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.server.entity.control.Axes;
import xyz.upperlevel.opencraft.server.entity.control.EntityAxes;
import xyz.upperlevel.opencraft.server.world.Location;

public class ControllableEntity implements Entity {

    @Getter
    @Setter
    @NonNull
    private Location loc = new Location();

    @Getter
    @NonNull
    private Vector3f vel = new Vector3f();

    @Getter
    @Setter
    @NonNull
    private Axes axes = new EntityAxes();

    public ControllableEntity() {
    }


    public void move(Vector3f dir, float sens) {
        vel.add(dir.mul(sens));
    }

    public void right(float sens) {
        move(axes.getRight(
                loc.getYaw(),
                loc.getPitch()
        ), sens);
    }

    public void left(float sens) {
        move(axes.getLeft(
                loc.getYaw(),
                loc.getPitch()
        ), sens);
    }

    public void up(float sens) {
        move(axes.getUp(
                loc.getYaw(),
                loc.getPitch()
        ), sens);
    }

    public void down(float sens) {
        move(axes.getDown(
                loc.getYaw(),
                loc.getPitch()
        ), sens);
    }

    public void forward(float sens) {
        move(axes.getForward(
                loc.getYaw(),
                loc.getPitch()
        ), sens);
    }

    public void backward(float sens) {
        move(axes.getBackward(
                loc.getYaw(),
                loc.getPitch()
        ), sens);
    }


    @Override
    public void tick() {
    }
}