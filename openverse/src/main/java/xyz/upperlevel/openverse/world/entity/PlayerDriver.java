package xyz.upperlevel.openverse.world.entity;

public interface EntityDriver {

    void forward(double sensitivity);

    void backward(double sensitivity);

    void left(double sensitivity);

    void right(double sensitivity);

    void up(double sensitivity);

    void down(double sensitivity);
}
