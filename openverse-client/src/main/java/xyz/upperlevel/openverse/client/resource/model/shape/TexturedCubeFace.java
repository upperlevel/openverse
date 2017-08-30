package xyz.upperlevel.openverse.client.resource.model.shape;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import xyz.upperlevel.openverse.client.resource.texture.Texture;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * A class representing the face of the cube shape.
 * It's only client-side and can have a texture or a color.
 */
@Getter
@Setter
public class TexturedCubeFace implements ClientShape {
    public static final int VERTICES_COUNT = 4;

    private final TexturedCube cube;
    private final CubeFacePosition position;
    private final Map<QuadVertexPosition, Vertex> vertices = new EnumMap<>(QuadVertexPosition.class);

    private Box box;
    private Texture texture;

    public TexturedCubeFace(TexturedCube cube, CubeFacePosition position, Config config) {
        this(cube, position);
        if (config.has("vertices")) {
            for (Config vrtCfg : config.getConfigList("vertices")) {
                QuadVertexPosition pos = QuadVertexPosition.valueOf(vrtCfg.getStringRequired("position"));
                vertices.put(pos, new QuadVertex(pos, vrtCfg));
            }
        }
    }

    public TexturedCubeFace(TexturedCube cube, CubeFacePosition position) {
        this.cube = cube;
        this.position = position;
        this.box = position.getBox(cube.getBox());
        setupVertices();
    }

    private void setupVertices() {
        for (QuadVertexPosition pos : QuadVertexPosition.values())
            vertices.put(pos, new QuadVertex(pos));
    }

    public Vertex getVertex(QuadVertexPosition position) {
        return vertices.get(position);
    }

    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    @Override
    public int getVerticesCount() {
        return VERTICES_COUNT;
    }

    /**
     * Sets the given color for all vertices contained in the face.
     * @param color the color
     */
    @Override
    public void setColor(Color color) {
        for (Vertex vertex : vertices.values())
            vertex.setColor(color);
    }

    @Override
    public int store(Matrix4f in, ByteBuffer buffer) {
        in = new Matrix4f(in)
                // put the face in the right cube position
                .translate(position.getDirection().mul(box.getSize()))
                .scale(box.getSize())
                // rotates the face to its position
                .translate(.5f, .5f, .5f)
                .rotate(position.getRotation())
                .translate(-.5f, -.5f, -.5f);
        int sz = 0;
        for (Vertex vrt : vertices.values())
            sz += vrt.store(in, buffer, texture.getLayer());
        return sz;
    }
}