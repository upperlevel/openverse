package xyz.upperlevel.openverse.resource.model;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.physic.Box;

// todo implement in client-side
public class Sphere implements ModelPart {

    @Getter
    private final int radius;

    @Getter
    private final Box box;

    public Sphere(int radius) {
        this.radius = radius;
        this.box = new Box(
                // min pos
                -radius,
                -radius,
                -radius,

                // max pos
                +radius,
                +radius,
                +radius
        );
    }
}
