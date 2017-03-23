package xyz.upperlevel.opencraft.server.entity;

import org.joml.Vector3f;
import xyz.upperlevel.opencraft.server.world.Location;

public interface Entity {

    Location getLoc();

    Vector3f getVel();

    void tick();
}
