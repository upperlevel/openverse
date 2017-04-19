package xyz.upperlevel.openverse.resource.model.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.openverse.client.resource.texture.Texture;
import xyz.upperlevel.ulge.util.Color;

public class CubeFace {

    @Getter
    private Cube cube;

    @Getter
    private CubeFacePosition position;

    @Getter
    @Setter
    private Quad quad;

    public CubeFace(@NonNull Cube cube, @NonNull CubeFacePosition position) {
        this.cube = cube;
        this.position = position;

        quad = new Quad(position.getBox(cube.getBox()));
    }

    public void setTexture(@NonNull Texture texture) {
        quad.setTexture(texture);
    }

    public void setColor(@NonNull Color color) {
        quad.setColor(color);
    }
}
