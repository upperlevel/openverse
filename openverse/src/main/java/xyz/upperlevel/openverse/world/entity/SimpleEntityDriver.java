package xyz.upperlevel.openverse.world.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.player.Player;

public class SimpleEntityDriver implements EntityDriver<Player> {
    private static final Vector3f UP = new Vector3f(0, 1, 0);

    private Matrix4f getOrientation(Entity entity) {
        Location location = entity.getLocation();

        return new Matrix4f().rotationXYZ((float)location.getPitch(), (float)location.getYaw(), 0.0f);
    }

    public Vector3f getForward(Entity entity) {
        return getOrientation(entity)
                .invertAffine()
                .transformDirection(new Vector3f(0f, 0f, -1f));
    }

    public Vector3f getRight(Entity entity) {
        return getOrientation(entity)
                .invertAffine()
                .transformDirection(new Vector3f(1f, 0f, 0f));
    }

    public Vector3f getUp(Entity entity) {
        return getOrientation(entity)
                .invertAffine()
                .transformDirection(new Vector3f(0f, 1f, 0f));
    }

    @Override
    public void forward(Player entity, double sensitivity) {
        entity.getLocation().add(getForward(entity).mul((float) sensitivity));
    }

    @Override
    public void backward(Player entity, double sensitivity) {
        entity.getLocation().mul(-((float) sensitivity));
    }

    @Override
    public void left(Player entity, double sensitivity) {
        entity.getLocation().add(getRight(entity).mul(-((float) sensitivity)));
    }

    @Override
    public void right(Player entity, double sensitivity) {
        entity.getLocation().add(getRight(entity).mul((float) sensitivity));
    }

    @Override
    public void up(Player entity, double sensitivity) {
        entity.getLocation().add(UP.mul((float) sensitivity, new Vector3f()));
    }

    @Override
    public void down(Player entity, double sensitivity) {
        entity.getLocation().add(UP.mul(-((float) sensitivity), new Vector3f()));
    }
}
