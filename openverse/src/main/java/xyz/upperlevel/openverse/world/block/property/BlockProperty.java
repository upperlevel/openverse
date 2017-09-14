package xyz.upperlevel.openverse.world.block.property;

import com.google.common.collect.ImmutableSet;

import java.util.Optional;

/**
 * This class represents a property of a {@link xyz.upperlevel.openverse.world.block.state.BlockState}
 * @param <T> the property type
 */
public interface BlockProperty<T extends Comparable<T>> {

    /**
     * Returns the name of the Property
     * @return the name of the Property
     */
    String getName();

    /**
     * Returns every possible value of this property
     * @return every value of this property
     */
    ImmutableSet<T> getPossibleValues();

    /**
     * Tries to parse the property, returns {@link Optional#empty()} if it failed to parse
     * @param value the string representing the property value
     * @return the value represented by the string or {@link Optional#empty()} if the parse failed
     */
    Optional<T> parse(String value);

    /**
     * Returns the name of the property's value
     * @param value the value to extract the name from
     * @return the name of the value
     */
    String getName(T value);
}
