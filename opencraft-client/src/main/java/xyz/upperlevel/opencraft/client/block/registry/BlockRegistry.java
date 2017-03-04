package xyz.upperlevel.opencraft.client.block.registry;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.block.*;
import xyz.upperlevel.opencraft.common.world.BridgeBlockType;
import xyz.upperlevel.ulge.util.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockRegistry {

    public static final BlockRegistry def = new BlockRegistry();

    static {
        ShapeComponent comp = new ShapeComponent();
        comp.getFace(BlockFacePosition.FORWARD).setColor(Color.RED);
        comp.getFace(BlockFacePosition.BACKWARD).setColor(Color.YELLOW);
        comp.getFace(BlockFacePosition.LEFT).setColor(Color.GREEN);
        comp.getFace(BlockFacePosition.RIGHT).setColor(Color.BLUE);
        comp.getFace(BlockFacePosition.UP).setColor(Color.AQUA);
        comp.getFace(BlockFacePosition.DOWN).setColor(Color.DARK_RED);
        def.register(BlockType.create("null_block", new BlockShape()));
                //.addComponent(comp)));

        def.register(BlockType.create("test_id", new BlockShape()
                .addComponent(comp)));
    }

    @Getter
    private List<BlockType> blocks = new ArrayList<>();

    public BlockRegistry() {
    }

    public xyz.upperlevel.opencraft.client.block.registry.BlockType getBlock(String id) {
        return blocks.stream()
                .filter(type -> type.getId().equalsIgnoreCase(id))
                .findAny()
                .orElse(null);
    }

    public xyz.upperlevel.opencraft.client.block.registry.BlockType getBlock(BridgeBlockType type) {
        return getBlock(type.getId());
    }

    public boolean isRegister(xyz.upperlevel.opencraft.client.block.registry.BlockType type) {
        return blocks.contains(type);
    }

    public BlockRegistry register(xyz.upperlevel.opencraft.client.block.registry.BlockType type) {
        Objects.requireNonNull(type, "type");
        blocks.add(type);
        return this;
    }

    public BlockRegistry unregister(xyz.upperlevel.opencraft.client.block.registry.BlockType type) {
        blocks.remove(type);
        return this;
    }

    public static BlockRegistry def() {
        return def;
    }
}