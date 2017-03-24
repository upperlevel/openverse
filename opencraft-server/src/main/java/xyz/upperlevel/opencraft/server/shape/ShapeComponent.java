package xyz.upperlevel.opencraft.server.shape;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.server.physic.collision.Box;

public class ShapeComponent {

    @Getter
    @Setter
    @NonNull
    private Box box;

    public ShapeComponent() {
    }

    public ShapeComponent(@NonNull Box box) {
        this.box = box;
    }
}
