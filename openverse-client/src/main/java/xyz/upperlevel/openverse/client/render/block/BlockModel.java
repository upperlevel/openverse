package xyz.upperlevel.openverse.client.render.block;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.openverse.util.math.Aabb3f;
import xyz.upperlevel.openverse.world.BlockFace;
import xyz.upperlevel.openverse.world.World;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class BlockModel {
    private Aabb3f aabb = Aabb3f.ZERO;
    private List<BlockPart> blockParts = new ArrayList<>();

    private Map<BlockFace, List<BlockPartFace>> externalFaces = new HashMap<>();

    public BlockModel(List<BlockPart> blockParts) {
        for (BlockPart bp : blockParts)
            addBlockPart(bp);
    }

    public boolean testAabbCarefully(Aabb3f aabb) {
        for (BlockPart part : blockParts)
            if (part.getAabb().inside(aabb))
                return true;
        return false;
    }

    /**
     * Loads external {@link BlockPartFace} from given {@link BlockPart}.
     * External means faces on the border of the model.
     */
    private void loadExternalFaces(BlockPart blockPart) {
        for (Map.Entry<BlockFace, BlockPartFace> face : blockPart.getFaces().entrySet()) {
            switch (face.getKey()) {
                case UP:
                    if (blockPart.getAabb().maxY < 1f)
                        break;
                case DOWN:
                    if (blockPart.getAabb().minY > 0f)
                        break;
                case RIGHT:
                    if (blockPart.getAabb().maxX < 1f)
                        break;
                case LEFT:
                    if (blockPart.getAabb().minX > 0f)
                        break;
                case FRONT:
                    if (blockPart.getAabb().minZ > 0f)
                        break;
                case BACK:
                    if (blockPart.getAabb().maxZ < 1f)
                        break;
                default:
                    externalFaces.computeIfAbsent(face.getKey(), (key) -> new ArrayList<>()).add(face.getValue());
            }
        }
    }

    public void addBlockPart(BlockPart blockPart) {
        Preconditions.checkNotNull(blockPart);
        blockParts.add(blockPart);
        aabb = aabb.union(blockPart.getAabb());
        loadExternalFaces(blockPart);
    }

    /**
     * Prepares the parts of this model to be used for rendering.
     */
    public void bake() {
        for (BlockPart part : blockParts) {
            part.bake();
        }
    }

    public int getVerticesCount() {
        int cnt = 0;
        for (BlockPart part : getBlockParts())
            cnt += part.getVerticesCount();
        return cnt;
    }

    public int getDataCount() {
        return getVerticesCount() * (3 + 3 + 1);
    }

    public int renderOnBuffer(World world, int x, int y, int z, ByteBuffer buffer) {
        int v = 0;
        for (BlockPart blockPart : blockParts) {
            v += blockPart.renderOnBuffer(world, x, y, z, buffer);
        }
        return v;
    }

    public BlockModel copy() {
        return new BlockModel(new ArrayList<>(blockParts));
    }
}