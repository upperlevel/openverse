package xyz.upperlevel.opencraft.client.asset.shape;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.asset.texture.Texture;
import xyz.upperlevel.opencraft.client.render.LocalWorld;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockCubeComponent implements BlockComponent {

    public static final int VERTICES_COUNT = BlockFace.VERTICES_COUNT * 6;

    public static final int DATA_COUNT = BlockFace.DATA_COUNT * VERTICES_COUNT;

    private Map<BlockFacePosition, BlockFace> faces = new HashMap<>();

    @Getter
    @NonNull
    private Zone3f zone;

    private void initDefaultFaces() {
        for (BlockFacePosition position : BlockFacePosition.values())
            faces.put(position, new BlockFace(BlockCubeComponent.this, position));
    }

    public BlockCubeComponent() {
    }

    public BlockCubeComponent(Zone3f zone) {
        this.zone = zone;
        initDefaultFaces();
    }

    public boolean isInside(float x, float y, float z) {
        return zone.isInside(x, y, z);
    }

    public boolean isInside(Zone3f zone) {
        return zone.isInside(zone);
    }

    public BlockCubeComponent setColor(Color color) {
        faces.values().forEach(face -> face.setColor(color));
        return this;
    }

    public BlockCubeComponent setTexture(Texture texture) {
        faces.values().forEach(face -> face.setTexture(texture));
        return this;
    }

    public BlockFace getFace(BlockFacePosition position) {
        return faces.get(position); // must be present since initialized in the constructor
    }

    public Collection<BlockFace> getFaces() {
        return faces.values();
    }

    @Override
    public int compile(Matrix4f matrix, ByteBuffer buffer) {
        matrix = new Matrix4f(matrix)
                .translate(zone.getSize()
                        .add(zone.getMinPosition().mul(2))
                        .mul(1f, 1f, -1f));

        int vertices = 0;
        for (BlockFace face : getFaces())
            vertices += face.compile(new Matrix4f(matrix), buffer);
        return vertices;
    }

    public boolean canCompile(BlockFace face, BlockShape relative) {
        return relative == null || relative.isEmpty() || relative.isTransparent() || !relative.isInside(face.getMirrorZone());
    }

    @Override
    public int cleanCompile(int x, int y, int z, LocalWorld world, Matrix4f matrix, ByteBuffer buffer) {
        int vertices = 0;
        for (BlockFacePosition position : BlockFacePosition.values()) {
            BlockFace face = getFace(position);
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