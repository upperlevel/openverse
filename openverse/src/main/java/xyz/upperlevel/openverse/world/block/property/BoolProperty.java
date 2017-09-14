package xyz.upperlevel.openverse.world.block.property;

import com.google.common.collect.ImmutableSet;

import java.util.Optional;

public class BoolProperty extends BaseBlockProperty<Boolean> {
    public static final ImmutableSet<Boolean> VALUES = ImmutableSet.of(true, false);

    public BoolProperty(String name) {
        super(name);
    }

    @Override
    public ImmutableSet<Boolean> getPossibleValues() {
        return VALUES;
    }

    @Override
    public Optional<Boolean> parse(String value) {
        switch (value.toLowerCase()) {
            case "true":
                return Optional.of(true);
            case "false":
                return Optional.of(false);
            default:
                return Optional.empty();
        }
    }

    @Override
    public String getName(Boolean value) {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return other instanceof BoolProperty && super.equals(other);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + VALUES.hashCode();
    }
}
