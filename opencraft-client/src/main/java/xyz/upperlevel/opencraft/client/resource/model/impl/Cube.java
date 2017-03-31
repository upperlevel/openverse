package xyz.upperlevel.opencraft.client.resource.model.impl;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.resource.model.VertexShape;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;
import xyz.upperlevel.opencraft.physic.Box;
import xyz.upperlevel.ulge.util.Color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static xyz.upperlevel.ulge.util.Color.*;


public class Cube implements VertexShape {

    private Map<CubeFacePosition, CubeFace> faces = new HashMap<>();

    {
        for (CubeFacePosition position : CubeFacePosition.values())
            faces.put(position, new CubeFace(this, position));
        {
            faces.get(CubeFacePosition.BACK).setColor(YELLOW);
            faces.get(CubeFacePosition.FRONT).setColor(RED);
            faces.get(CubeFacePosition.RIGHT).setColor(GREEN);
            faces.get(CubeFacePosition.LEFT).setColor(BLUE);
            faces.get(CubeFacePosition.DOWN).setColor(AQUA);
            faces.get(CubeFacePosition.UP).setColor(WHITE);
        }
    }

    @Getter
    private Box box = new Box();

    public Cube() {
    }

    public Cube(Box box) {
        this.box = box;
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
