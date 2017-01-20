package xyz.upperlevel.opencraft.world.block.shape;

import java.io.*;

public class BlockShapeLoader {

    public static final BlockShapeLoader $ = new BlockShapeLoader();

    public BlockShape load(InputStream stream) throws IOException {
        return null;
    }

    public BlockShape load(File file) throws IOException {
        return load(new FileInputStream(file));
    }

    public static BlockShapeLoader $() {
        return $;
    }
}
