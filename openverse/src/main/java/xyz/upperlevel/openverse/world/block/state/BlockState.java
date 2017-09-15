package xyz.upperlevel.openverse.world.block.state;

import com.google.common.collect.ImmutableMap;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.property.BlockProperty;

public interface BlockState {

    /**
     * Returns the value of the specified property in this {@link BlockState}
     * @param property the property to read the value from
     * @param <T> the property type
     * @return the property's value or null if the property isn't present
     */
    <T extends Comparable<T>> T get(BlockProperty<T> property);

    /**
     * Returns another {@link BlockState} with the specified {@link BlockProperty} modified
     * @param property the property to change
     * @param value the new property's value
     * @param <T> the property type
     * @return a different BlockState with the requested properties
     * @throws IllegalArgumentException it the property isn't in the state or if the value isn't allowed
     */
    <T extends Comparable<T>> BlockState with(BlockProperty<T> property, T value);

    /**
     * Returns all the {@link BlockProperty} (and their values) in this {@link BlockState}
     * @return all the {@link BlockProperty} in this {@link BlockState}
     */
    ImmutableMap<BlockProperty<?>, Comparable<?>> getProperties();

    /**
     * Checks if the property is included in this {@link BlockState}
     * @param property the property to check
     * @return true only if the property is included in this {@link BlockState}
     */
    default boolean hasProperty(BlockProperty<?> property) {
        return getProperties().containsKey(property);
    }

    /**
     * Returns the {@link BlockType} that declared this state
     * @return the {@link BlockType} that declared this state
     */
    BlockType getBlockType();

    /**
     * Returns the id of the state (must be mapped with {@link BlockStateRegistry#getState(int)}
     * @return the id of the BlockState
     */
    int getId();
}
