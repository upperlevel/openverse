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

public class Cube implements ModelPart {

    public static final int VERTICES_COUNT = CubeFace.VERTICES_COUNT * 6;

    public static final int DATA_COUNT = CubeFace.DATA_COUNT * VERTICES_COUNT;

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

    @Override
    public int getVertices() {
        return VERTICES_COUNT;
    }

    @Override
    public int compile(Matrix4f matrix, ByteBuffer buffer) {
        matrix = new Matrix4f(matrix)
                .translate(zone.getSize()
                        .add(box.x, box.y, box.z)
                        .mul(1f, 1f, -1f));

        int vertices = 0;
        for (CubeFace face : getFaces())
            vertices += face.compile(new Matrix4f(matrix), buffer);
        return vertices;
    }

    public boolean canCompile(CubeFace face, BlockShape relative) {
        return relative == null || relative.isEmpty() || relative.isTransparent() || !relative.isInside(face.getMirrorZone());
    }

    @Override
    public int cleanCompile(int x, int y, int z, WorldView world, Matrix4f matrix, ByteBuffer buffer) {
        int vertices = 0;
        for (CubeFacePosition position : CubeFacePosition.values()) {
            CubeFace face = getFace(position);
            // if the face is visible
            if (canCompile(face, world.getShape(
                    x + position.getDirectionX(),
                    y + position.getDirectionY(),
                    z + position.getDirectionZ()))) {
                // compiles it
                vertices += face.compile(matrix, buffer);
            }
        }
        return vertices;
    }
}