package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.util.math.Aabb2f;
import xyz.upperlevel.openverse.util.math.Aabb3f;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.World;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class BlockPartFace {
    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int BOTTOM_RIGHT = 2;
    public static final int BOTTOM_LEFT = 3;

    private final BlockPart blockPart;
    private final BlockFace blockFace;
    private final Aabb2f aabb;
    private final Path textureLocation;

    private float[] verticesX = new float[4];
    private float[] verticesY = new float[4];
    private float[] verticesZ = new float[4];
    private float[] verticesU = new float[4];
    private float[] verticesV = new float[4];
    private int textureLayer = -1;

    private boolean baked;

    private void bakeVertexPosition(int vertexEnum, float x, float y, float z) {
        verticesX[vertexEnum] = x;
        verticesY[vertexEnum] = y;
        verticesZ[vertexEnum] = z;
    }

    private void bakeVertexUv(int vertexEnum, float u, float v) {
        verticesU[vertexEnum] = u;
        verticesV[vertexEnum] = v;
    }

    private void bakeTexture() {
        textureLayer = TextureBakery.getLayer(textureLocation);
    }

    /**
     * Extract all the four vertices of the quad representing the face.
     */
    public void bake() {
        Aabb3f aabb = blockPart.getAabb();
        switch (blockFace) {
            case UP:
                bakeVertexPosition(TOP_LEFT, aabb.minX, aabb.maxY, aabb.maxZ);
                bakeVertexPosition(TOP_RIGHT, aabb.maxX, aabb.maxY, aabb.maxZ);
                bakeVertexPosition(BOTTOM_RIGHT, aabb.maxX, aabb.maxY, aabb.minZ);
                bakeVertexPosition(BOTTOM_LEFT, aabb.minX, aabb.maxY, aabb.minZ);
                break;
            case DOWN:
                bakeVertexPosition(TOP_LEFT, aabb.maxX, aabb.minY, aabb.maxZ);
                bakeVertexPosition(TOP_RIGHT, aabb.minX, aabb.minY, aabb.maxZ);
                bakeVertexPosition(BOTTOM_RIGHT, aabb.minX, aabb.minY, aabb.minZ);
                bakeVertexPosition(BOTTOM_LEFT, aabb.maxX, aabb.minY, aabb.minZ);
                break;
            case FRONT:
                bakeVertexPosition(TOP_LEFT, aabb.minX, aabb.maxY, aabb.minZ);
                bakeVertexPosition(TOP_RIGHT, aabb.maxX, aabb.maxY, aabb.minZ);
                bakeVertexPosition(BOTTOM_RIGHT, aabb.maxX, aabb.minY, aabb.minZ);
                bakeVertexPosition(BOTTOM_LEFT, aabb.minX, aabb.minY, aabb.minZ);
                break;
            case BACK:
                bakeVertexPosition(TOP_LEFT, aabb.maxX, aabb.maxY, aabb.maxZ);
                bakeVertexPosition(TOP_RIGHT, aabb.minX, aabb.maxY, aabb.maxZ);
                bakeVertexPosition(BOTTOM_RIGHT, aabb.minX, aabb.minY, aabb.maxZ);
                bakeVertexPosition(BOTTOM_LEFT, aabb.maxX, aabb.minY, aabb.maxZ);
                break;
            case RIGHT:
                bakeVertexPosition(TOP_LEFT, aabb.maxX, aabb.maxY, aabb.minZ);
                bakeVertexPosition(TOP_RIGHT, aabb.maxX, aabb.maxY, aabb.maxZ);
                bakeVertexPosition(BOTTOM_RIGHT, aabb.maxX, aabb.minY, aabb.maxZ);
                bakeVertexPosition(BOTTOM_LEFT, aabb.maxX, aabb.minY, aabb.minZ);
                break;
            case LEFT:
                bakeVertexPosition(TOP_LEFT, aabb.minX, aabb.maxY, aabb.maxZ);
                bakeVertexPosition(TOP_RIGHT, aabb.minX, aabb.maxY, aabb.minZ);
                bakeVertexPosition(BOTTOM_RIGHT, aabb.minX, aabb.minY, aabb.minZ);
                bakeVertexPosition(BOTTOM_LEFT, aabb.minX, aabb.minY, aabb.maxZ);
                break;
        }
        bakeVertexUv(TOP_LEFT, 0, 0);
        bakeVertexUv(TOP_RIGHT, 1f, 0);
        bakeVertexUv(BOTTOM_RIGHT, 1f, 1f);
        bakeVertexUv(BOTTOM_LEFT, 0, 1f);
        bakeTexture();
        baked = true;
    }

    /**
     * Is the face ready to be rendered?
     *
     * @return {@code true} if the face is baked, otherwise {@code false}
     */
    public boolean isBaked() {
        return baked;
    }

    public BlockPartFace(BlockPart blockPart, BlockFace blockFace, Config config) {
        this.blockPart = blockPart;
        this.blockFace = blockFace;
        this.aabb = blockFace.resolveAabb(blockPart.getAabb());
        this.textureLocation = Paths.get(config.getString("texture"));
    }

    private boolean shouldBeRendered(World world, int x, int y, int z) {
        if (!baked)
            return false;
        BlockModel relModel = BlockTypeModelMapper.model(world.getBlockState(x + blockFace.offsetX, y + blockFace.offsetY, z + blockFace.offsetZ));
        if (relModel != null) {
            List<BlockPartFace> extFac = relModel.getExternalFaces().get(blockFace.getOpposite());
            if (extFac != null) {
                for (BlockPartFace touching : extFac) {
                    if (touching.aabb.inside(aabb))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Renders baked model on the given buffer only if visible.
     */
    public int renderOnBuffer(World world, int x, int y, int z, ByteBuffer buffer) {
        // Checks if the face is hidden
        if (!shouldBeRendered(world, x, y, z))
            return 0;

        float lightVal = world.getBlockLight(x + blockFace.offsetX, y + blockFace.offsetY, z + blockFace.offsetZ) / 15f;
        float skylightVal = world.getBlockSkylight(x + blockFace.offsetX, y + blockFace.offsetY, z + blockFace.offsetZ) / 15f;

        for (int i = 0; i < 4; i++) {
            // Adds block vertices to the chunk
            buffer.putFloat(x + verticesX[i])
                    .putFloat(y + verticesY[i])
                    .putFloat(z + verticesZ[i])

                    .putFloat(verticesU[i])
                    .putFloat(verticesV[i])
                    .putFloat(textureLayer)

                    .putFloat(lightVal)
                    .putFloat(skylightVal);
        }
        return 4;
    }

    public static BlockPartFace deserialize(BlockPart part, BlockFace blockFace, Config config) {
        return new BlockPartFace(part, blockFace, config);
    }
}
