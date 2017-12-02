package xyz.upperlevel.openverse.world.block.property;

import com.google.common.collect.ImmutableSet;

import java.util.Optional;
import java.util.stream.IntStream;

public class IntProperty extends BaseBlockProperty<Integer> {
    private final ImmutableSet<Integer> values;
    private final int min;
    private final int max;

    public IntProperty(String name, int min, int max) {
        super(name);
        if (min < 0 || max <= min) {
            throw new IllegalArgumentException("Illegal range (min: " + min + ", max:" + max + ")");
        }
        this.min = min;
        this.max = max;
        this.values = IntStream.range(min, max).boxed().collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public ImmutableSet<Integer> getPossibleValues() {
        return values;
    }

    @Override
    public Optional<Integer> parse(String value) {
        int res;
        try {
            res = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        if (res < min || res >= max) {
            return Optional.empty();
        }
        return Optional.of(res);
    }

    @Override
    public String getName(Integer value) {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof IntProperty && super.equals(other)) {
            return  this.min == ((IntProperty) other).min &&
                    this.max == ((IntProperty) other).max;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + values.hashCode();
    }

    public static IntProperty of(String name, int min, int max) {
        return new IntProperty(name, min, max);
    }

    public static IntProperty of(String name, int max) {
        return new IntProperty(name, 0, max);
    }
}
