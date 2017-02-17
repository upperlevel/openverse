package xyz.upperlevel.opencraft.client.controller;

public interface ControllableEntity {

    void move(double x, double y ,double z);

    void rotate(float yaw, float pitch);

    void moveAndRotate(double x, double y, double z, float yaw, float pitch);
}