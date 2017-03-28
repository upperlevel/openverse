package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;
import xyz.upperlevel.opencraft.common.physic.collision.Box;
import xyz.upperlevel.ulge.util.Color;

import java.util.Collection;
import java.util.HashMap;


public class Cube implements Model {

    private Map<CubeFacePosition, CubeFace> faces = new HashMap<>();

    {
        for (CubeFacePosition position : CubeFacePosition.values())
            faces.put(position, new CubeFace(this, position));
        {
            faces.get(BACKWARD).setColor(YELLOW);
            faces.get(FORWARD).setColor(RED);
            faces.get(RIGHT).setColor(GREEN);
            faces.get(LEFT).setColor(BLUE);
            faces.get(DOWN).setColor(AQUA);
            faces.get(UP).setColor(WHITE);
        }
    }
    @Getter
    private Box box = new Box();

    public Cube() {
    }

    public Cube(Box box) {
        this.box = box;
    }

    @Override
    public String getId() {
        return "cube";
    }

    public void setTexture(Texture texture) {
        getFaces().forEach(f -> f.setTexture(texture));
    }

    public void setColor(Color color) {
        getFaces().forEach(f -> f.setColor(color));
    }

    public CubeFace getFace(CubeFacePosition position) {
        return faces.get(position);
    }

    public Collection<CubeFace> getFaces() {
        return faces.values();
    }
}
