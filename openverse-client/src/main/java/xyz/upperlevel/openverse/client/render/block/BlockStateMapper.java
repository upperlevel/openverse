package xyz.upperlevel.openverse.client.render.block;

import com.google.gson.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.property.BlockProperty;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link BlockStateMapper} associates each {@link BlockState} to a {@link BlockModel}.
 */
@Getter
@RequiredArgsConstructor
public class BlockStateMapper {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(BlockStateMapper.class, new Deserializer())
            .create();

    private final BlockType type;
    private final Map<BlockState, BlockModel> models = new HashMap<>();


    /**
     * Retrieves the model for the given {@link BlockState}.
     *
     * @param state the state
     * @return the model
     */
    public BlockModel getModels(BlockState state) {
        return models.get(state);
    }


    public static BlockStateMapper deserialize(File file) { // todo get file somehow
        try {
            return GSON.fromJson(new FileReader(file), BlockStateMapper.class);
        } catch (FileNotFoundException ignored) {
            return null;
        }
    }


    public static class Deserializer implements JsonDeserializer<BlockStateMapper> {
        @Override
        public BlockStateMapper deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            BlockType type = null; // todo get type in someway
            BlockStateMapper res = new BlockStateMapper(type);
            BlockStateRegistry reg = type.getStateRegistry();
            BlockState current = null;
            for (JsonElement json : element.getAsJsonObject().getAsJsonArray()) {
                BlockProperty<?> p = reg.getProperty(json.getAsString());
                Optional<?> val = p.parse(json.getAsJsonObject().getAsString());
                // if the value is valid then we can go on
                if (val.isPresent()) {
                    current = current.with(p, val.get());
                    res.models.put(current, m);
                }
            }
            return res;
        }
    }
}