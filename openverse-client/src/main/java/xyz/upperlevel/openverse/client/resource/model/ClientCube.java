package xyz.upperlevel.openverse.client.resource.model;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.client.resource.Texture;
import xyz.upperlevel.openverse.client.resource.TextureBakery;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.resource.model.Cube;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A client cube has faces and each face has a color and a texture.
 */
public class ClientCube extends Cube implements ClientModelPart {

    @Getter
    private final Map<CubeFacePosition, CubeFace> facesMap = new HashMap<CubeFacePosition, CubeFace>() {{
        for (CubeFacePosition pos : CubeFacePosition.values())
            facesMap.put(pos, new CubeFace(ClientCube.this, pos));
    }};

    @Getter
    private final Vector3f position;

    @Getter
    private final Vector3f size;

    public ClientCube(Box box) {
        super(box);

        // gets pos and size - optimizations
        position = new Vector3f(
                (float) box.x,
                (float) box.y,
                (float) box.z
        );
        size = new Vector3f(
                (float) box.width,
                (float) box.height,
                (float) box.depth
        );
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

    @Override
    public int getVerticesCount() {
        return 8;
    }

    @Override
    public int getDataCount() {
        return -1; // todo 8 * 4;
    }

    @Override
    public int compile(TextureBakery bakery, Matrix4f in, ByteBuffer out) {
        // moves the model part to its position related to block space
        in = new Matrix4f(in).translate(size.add(position));

        int vrt = 0;
        for (CubeFace f : getFaces())
            vrt += f.compile(in, out);
        return vrt;
    }
}
