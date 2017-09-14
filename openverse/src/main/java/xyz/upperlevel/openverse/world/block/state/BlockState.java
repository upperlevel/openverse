package xyz.upperlevel.openverse.world.block.state;

import com.google.common.collect.ImmutableMap;
import xyz.upperlevel.openverse.world.block.property.BlockProperty;

public interface BlockState {
    <T extends Comparable<T>> T get(BlockProperty<T> property);

    <T extends Comparable<T>> BlockState with(BlockProperty<T> property, T value);

    ImmutableMap<BlockProperty<?>, Comparable<?>> getProperties();
}
