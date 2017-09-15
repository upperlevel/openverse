package xyz.upperlevel.openverse.resource.block;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;

@Getter
public class BlockType {
    private final String id;
    @Setter
    private int rawId;
    private Model model;
    private boolean solid;

    protected final BlockStateRegistry blockState;
    private BlockState defaultBlockState;

    public BlockType(String id, Config config) {
        this.id = id;
        if (config.has("model")) {
            this.model = Openverse.resources().models().entry(config.getString("model"));
            if (model != null)
                Openverse.logger().info("Found model for block_type \"" + id + "\" named \"" + config.getString("model") + "\" of class " + model.getClass().getSimpleName());
        }
        this.solid = config.getBool("solid", true);
        this.blockState = createBlockState();
        setDefaultState(blockState.getDefaultState());
    }


    public BlockStateRegistry createBlockState() {
        return BlockStateRegistry.of(this);//Creates a BlockStateRegistry with no propriety
    }

    public BlockState getDefaultState() {
        return defaultBlockState;
    }

    public void setDefaultState(BlockState state) {
        this.defaultBlockState = state;
    }

    public BlockState getBlockState(int meta) {
        return blockState.getState(meta);
    }

    public int getStateMeta(BlockState state) {
        return state.getId();
    }

    public int getFullId(BlockState state) {
        return  rawId | (state.getId() & 0xF);
    }
}