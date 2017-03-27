package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

public class Cube implements ModelPart {

    @Getter
    private Box box = new Box();

    public Cube() {
    }
}
