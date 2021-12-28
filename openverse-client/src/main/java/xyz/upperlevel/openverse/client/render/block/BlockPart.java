package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.util.math.Aabb3f;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.World;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BlockPart {
    @Getter
    private final Aabb3f aabb;

    @Getter
    private Map<BlockFace, BlockPartFace> faces = new HashMap<>();

    private BlockFace parseFacing(String string) {
        return BlockFace.valueOf(string.toUpperCase(Locale.ENGLISH).replace("_", " "));
    }

    @SuppressWarnings("unchecked")
    public BlockPart(Config config) {
        Config fromCfg = config.getConfigRequired("from");
        Config toCfg = config.getConfigRequired("to");
        aabb = new Aabb3f(
                fromCfg.getFloatRequired("x"),
                fromCfg.getFloatRequired("y"),
                fromCfg.getFloatRequired("z"),
                toCfg.getFloatRequired("x"),
                toCfg.getFloatRequired("y"),
                toCfg.getFloatRequired("z")
        );
        for (Map.Entry<String, Object> faceMap : config.getSectionRequired("faces").entrySet()) {
            BlockFace fac = parseFacing(faceMap.getKey());
            if (fac != null)
                faces.put(fac, BlockPartFace.deserialize(this, fac, Config.wrap((Map<String, Object>) faceMap.getValue())));
        }
    }

    public int getVerticesCount() {
        return 6 * 4;
    }

    /**
     * Prepares the faces of this part to be used for rendering.
     */
    public void bake() {
        for (BlockPartFace face : faces.values()) {
            face.bake();
        }
    }


    public int renderOnBuffer(World world, int x, int y, int z, ByteBuffer buffer) {
        int v = 0;
        for (BlockFace f : BlockFace.values()) {
            v += faces.get(f).renderOnBuffer(world, x, y, z, buffer);
        }
        return v;
    }

    public static BlockPart deserialize(Config config) {
        return new BlockPart(config);
    }
}
