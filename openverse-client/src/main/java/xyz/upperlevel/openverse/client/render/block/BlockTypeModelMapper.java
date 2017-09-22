package xyz.upperlevel.openverse.client.render.block;

import com.google.gson.Gson;
import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class BlockTypeModelMapper {
    private static final Gson GSON = new Gson();
    private static Map<BlockType, BlockModelMapper> modelMappers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static BlockModelMapper load(BlockType type, Path path) {
        Openverse.logger().info("Attempting to associate type " + type.getId() + " at \"" + path + "\"...");
        BlockModelMapper models;
        try {
            models = new BlockModelMapper(type, Config.wrap(GSON.fromJson(new FileReader(path.toFile()), Map.class)));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        modelMappers.put(type, models);
        Openverse.logger().info("Block type associated!");
        return models;
    }

    public static void register(BlockType type, BlockModelMapper mapper) {
        modelMappers.put(type, mapper);
    }

    public static BlockModelMapper modelMapper(BlockType type) {
        return modelMappers.get(type);
    }

    public static BlockModel model(BlockState state) {
        BlockModelMapper bsm = modelMapper(state.getBlockType());
        if (bsm != null) {
            return bsm.getModel(state);
        }
        return null;
    }
}
