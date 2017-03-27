package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;

public class CubeFace {

    @Getter
    private Cube cube;

    @Getter
    private FacePosition position;

    @Getter
    @Setter
    private Texture texture;

    public CubeFace(@NonNull Cube cube, @NonNull FacePosition position) {
        this.cube = cube;
        this.position = position;
    }
}
