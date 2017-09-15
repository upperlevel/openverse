package xyz.upperlevel.openverse.client.render.block.blockentity;

import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class associates each {@link BlockEntity} to its {@link BlockEntityRenderer}.
 */
@SuppressWarnings("unchecked")
public class BlockEntityRendererDispatcher {
    public static final BlockEntityRendererDispatcher INSTANCE = new BlockEntityRendererDispatcher();

    private final Map<Class<? extends BlockEntity>, BlockEntityRenderer> renderers = new HashMap<>();


    private BlockEntityRendererDispatcher() {
        // todo register here defaults
    }


    public <T extends BlockEntity> void register(Class<T> blockEntityClass, T blockEntity) {
        renderers.put(blockEntityClass, (BlockEntityRenderer) blockEntity);
    }

    public <T extends BlockEntity> BlockEntityRenderer<T> getRenderer(Class<T> blockEntityClass) {
        return (BlockEntityRenderer<T>) renderers.get(blockEntityClass);
    }

    public <T extends BlockEntity> BlockEntityRenderer<T> getRenderer(T blockEntity) {
        return (BlockEntityRenderer<T>) renderers.get(blockEntity.getClass());
    }

    public Collection<BlockEntityRenderer> getRenderers() {
        return renderers.values();
    }


    public <T extends BlockEntity> void render(T blockEntity) {
        BlockEntityRenderer<T> ber = getRenderer(blockEntity);
        if (ber != null) {
            ber.render(blockEntity);
        }
    }
}