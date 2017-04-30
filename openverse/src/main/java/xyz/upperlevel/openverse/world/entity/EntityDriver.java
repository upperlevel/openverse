package xyz.upperlevel.openverse.world.entity;

public interface EntityDriver<E extends Entity> {

    void forward(E entity, double sensitivity);

    void backward(E entity, double sensitivity);

    void left(E entity, double sensitivity);

    void right(E entity, double sensitivity);

    void up(E entity, double sensitivity);

    void down(E entity, double sensitivity);
}
