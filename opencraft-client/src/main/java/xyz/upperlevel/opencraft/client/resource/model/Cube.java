package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;
import xyz.upperlevel.opencraft.client.view.WorldView;
import xyz.upperlevel.opencraft.common.physic.collision.Box;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static xyz.upperlevel.opencraft.client.resource.model.CubeFacePosition.*;
import static xyz.upperlevel.ulge.util.Color.*;

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
    @NonNull
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