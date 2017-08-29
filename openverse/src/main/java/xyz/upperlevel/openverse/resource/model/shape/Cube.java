package xyz.upperlevel.openverse.resource.model.shape;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.resource.model.shape.Shape;
import xyz.upperlevel.openverse.util.config.Config;

@Getter
@Setter
public class Cube implements Shape {
    private float x, y, z;
    private float width, height, length;
    private Box box;

    public Cube() {
    }

    public Cube(Config config) {
        this.x = config.getFloat("x");
        this.y = config.getFloat("y");
        this.z = config.getFloat("z");
        this.width = config.getFloat("width");
        this.height = config.getFloat("height");
        this.length = config.getFloat("length");
        this.box = new Box(x, y, z, width, height, length);
    }
}
