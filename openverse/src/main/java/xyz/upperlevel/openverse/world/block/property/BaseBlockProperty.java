package xyz.upperlevel.openverse.world.block.property;

public abstract class BaseBlockProperty<T extends Comparable<T>> implements BlockProperty<T> {
    private final String name;

    public BaseBlockProperty(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof BaseBlockProperty) {
            return this.name.equals(((BaseBlockProperty) other).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
