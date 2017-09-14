package xyz.upperlevel.openverse.world.block.property;

import com.google.common.collect.ImmutableSet;

import java.util.Optional;

public interface BlockProperty<T extends Comparable<T>> {
    String getName();

    ImmutableSet<T> getPossibleValues();

    Optional<T> parse(String value);

    String getName(T property);
}
