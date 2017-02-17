package xyz.upperlevel.opencraft.client.controller;

public interface ControllableEntity {

    void teleport(double x, double y, double z);

    void teleport(double x, double y, double z, float yaw, float pitch);

    void teleport(float yaw, float pitch);
}