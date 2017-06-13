package xyz.upperlevel.openverse.resource.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.openverse.physic.Box;

// this cube class have not to contain faces
public class Cube implements Shape {

    @Getter
    @Setter
    @NonNull
    private Box box;

    public Cube() {
        this.box = new Box();
    }

    public Cube(Box box) {
        this.box = box;
    }
}
