package xyz.upperlevel.opencraft.client.asset.shape;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.block.BlockFacePosition;
import xyz.upperlevel.opencraft.client.render.RenderArea;
import xyz.upperlevel.opencraft.client.texture.TextureRegistry;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockShapeCubeComponent implements BlockShapeComponent {

    public static final int VERTICES_COUNT = BlockFace.VERTICES_COUNT * 6;

    public static final int DATA_COUNT = BlockFace.DATA_COUNT * VERTICES_COUNT;

    private Map<BlockFacePosition, BlockFace> faces = new HashMap<>();

    @Getter
    @NonNull
    private Zone3f zone;

    private void initDefaultFaces() {
        for (BlockFacePosition position : BlockFacePosition.values())
            faces.put(position, new BlockFace(BlockShapeCubeComponent.this, position));
    }

    public BlockShapeCubeComponent() {
    }

    public BlockShapeCubeComponent(Zone3f zone) {
        this.zone = zone;
        initDefaultFaces();
    }

    public boolean isInside(Zone3f zone) {
        return zone.isInside(zone);
    }

    public BlockShapeCubeComponent setColor(Color color) {
        faces.values().forEach(face -> face.setColor(color));
        return this;
    }

    public BlockShapeCubeComponent setTexture(TextureRegistry.SpriteTexture texture) {
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
    public void compile(Matrix4f matrix, ByteBuffer buffer) {
        matrix = new Matrix4f(matrix)
                .translate(zone.getSize()
                        .add(zone.getMinPosition().mul(2))
                        .mul(1f, 1f, -1f));

        for (BlockFace face : getFaces())
            face.compile(new Matrix4f(matrix), buffer);
    }

    public boolean canCompile(BlockFace face, BlockShape relative) {
        return relative == null || relative.isTransparent() || !relative.isInside(face.getMirrorZone());
    }

    @Override
    public void cleanCompile(int x, int y, int z, RenderArea area, Matrix4f matrix, ByteBuffer buffer) {
        BlockShape
                rel_right,
                rel_up,
                rel_forward,
                rel_left,
                rel_down,
                rel_backward;
        
        BlockFace
                f_right,
                f_up,
                f_forward,
                f_left,
                f_down,
                f_backward;

        // relatives
        rel_right    = area.getShape(x + 1, y, z);
        rel_up       = area.getShape(x, y + 1, z);
        rel_forward  = area.getShape(x, y, z + 1);
        rel_left     = area.getShape(x - 1, y, z);
        rel_down     = area.getShape(x, y - 1, z);
        rel_backward = area.getShape(x, y, z - 1);

        // faces
        f_right      = getFace(BlockFacePosition.RIGHT);
        f_up         = getFace(BlockFacePosition.UP);
        f_forward    = getFace(BlockFacePosition.FORWARD);
        f_left       = getFace(BlockFacePosition.LEFT);
        f_down       = getFace(BlockFacePosition.DOWN);
        f_backward   = getFace(BlockFacePosition.BACKWARD);

        if (canCompile(f_right, rel_right))
            f_right.compile(matrix, buffer);

        if (canCompile(f_up, rel_up))
            f_up.compile(matrix, buffer);

        if (canCompile(f_forward, rel_forward))
            f_forward.compile(matrix, buffer);

        if (canCompile(f_left, rel_left))
            f_left.compile(matrix, buffer);

        if (canCompile(f_down, rel_down))
            f_down.compile(matrix, buffer);

        if (canCompile(f_backward, rel_backward))
            f_backward.compile(matrix, buffer);
    }
}