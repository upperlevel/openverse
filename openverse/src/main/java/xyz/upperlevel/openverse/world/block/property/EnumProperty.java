package xyz.upperlevel.openverse.world.block.property;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EnumProperty<T extends Enum<T>> extends BaseBlockProperty<T> {
    private final ImmutableSet<T> values;
    private final Map<String, T> bakedByName;

    public EnumProperty(String name, ImmutableSet<T> values) {
        super(name);
        this.values = values;
        this.bakedByName = values.stream().collect(Collectors.toMap(e -> e.name().toUpperCase(), Function.identity()));
    }

    @Override
    public ImmutableSet<T> getPossibleValues() {
        return values;
    }

    @Override
    public Optional<T> parse(String value) {
        return Optional.ofNullable(bakedByName.get(value));
    }

    @Override
    public String getName(T property) {
        return property.name();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof EnumProperty && super.equals(other)) {
            return this.values.equals(((EnumProperty) other).values);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + values.hashCode();
    }


    @SafeVarargs
    public static <T extends Enum<T>> EnumProperty<T> of(String name, T... values) {
        return new EnumProperty<>(name, Sets.immutableEnumSet(Arrays.asList(values)));
    }

    public static <T extends Enum<T>> EnumProperty<T> of(String name, Collection<T> values) {
        return new EnumProperty<>(name, Sets.immutableEnumSet(values));
    }

    public static <T extends Enum<T>> EnumProperty<T> of(String name, Class<T> clazz) {
        return new EnumProperty<>(name, Sets.immutableEnumSet(Arrays.asList(clazz.getEnumConstants())));
    }

    public static <T extends Enum<T>> EnumProperty<T> of(String name, Class<T> clazz, Predicate<T> filter) {
        return new EnumProperty<>(name,
                Arrays.stream(clazz.getEnumConstants())
                        .filter(filter)
                        .collect(Sets.toImmutableEnumSet())
        );
    }
}
