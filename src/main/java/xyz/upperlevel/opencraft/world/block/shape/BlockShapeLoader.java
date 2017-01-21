package xyz.upperlevel.opencraft.world.block.shape;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class BlockShapeLoader {

    public static final BlockShapeLoader $ = new BlockShapeLoader();

    @SuppressWarnings("unchecked")
    public BlockShape load(InputStream stream) throws IOException {
        Map<String, Object> data = (Map<String, Object>) new Yaml().load(stream);

    }

    public BlockShape load(File file) throws IOException {
        return load(new FileInputStream(file));
    }

    public static BlockShapeLoader $() {
        return $;
    }
}
