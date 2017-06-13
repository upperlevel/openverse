package xyz.upperlevel.openverse.client.resource.model;

import xyz.upperlevel.openverse.client.resource.Texture;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.resource.model.Cube;
import xyz.upperlevel.ulge.util.Color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A client-side cube has faces and each face has a color and a texture.
 */
public class ClientCube extends Cube implements ClientShape {

    private final Map<CubeFacePosition, CubeFace> facesMap = new HashMap<CubeFacePosition, CubeFace>() {{
        for (CubeFacePosition pos : CubeFacePosition.values())
            facesMap.put(pos, new CubeFace(ClientCube.this, pos));
    }};

    public ClientCube(Box box) {
        super(box);
    }

    // sets color for all faces
    public void setColor(Color color) {
        getFaces().forEach(face -> face.setColor(color));
    }

    // sets texture for all faces
    public void setTexture(Texture texture) {
        getFaces().forEach(face -> face.setTexture(texture));
    }

    public CubeFace getFace(CubeFacePosition position) {
        return facesMap.get(position);
    }

    public Collection<CubeFace> getFaces() {
        return facesMap.values();
    }
}
