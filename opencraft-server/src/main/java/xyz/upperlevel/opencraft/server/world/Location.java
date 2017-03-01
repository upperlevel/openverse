package xyz.upperlevel.opencraft.server.world;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
public class Location {

    @Getter
    @Setter
    @NonNull
    private World world;

    @Getter
    private float x, y, z, yaw, pitch;

    public Location(World world) {
        Objects.requireNonNull(world, "world");
        this.world = world;
    }
}
