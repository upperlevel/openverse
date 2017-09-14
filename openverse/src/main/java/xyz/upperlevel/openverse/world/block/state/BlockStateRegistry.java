package xyz.upperlevel.openverse.world.block.state;

import com.google.common.collect.*;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.block.property.BlockProperty;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockStateRegistry {
    private final BlockType type;
    private final ImmutableList<SimpleBlockState> states;
    private final ImmutableMap<String, BlockProperty<?>> nameBakedProperties;

    public BlockStateRegistry(BlockType type, Collection<BlockProperty<?>> properties) {
        this.type = type;

        nameBakedProperties = properties.stream().collect(ImmutableMap.toImmutableMap(BlockProperty::getName, Function.identity()));

        Set<List<Comparable<?>>> producs = Sets.cartesianProduct(properties.stream().map(BlockProperty::getPossibleValues).collect(Collectors.toList()));

        ImmutableList.Builder<SimpleBlockState> states = ImmutableList.builder();
        Map<Map<BlockProperty<?>, Comparable<?>>, SimpleBlockState> propertyMappedStates = new HashMap<>();

        int index = 0;
        for (List<Comparable<?>> values : producs) {
            ImmutableMap<BlockProperty<?>, Comparable<?>> stateProperties = mapOf(properties, values);
            SimpleBlockState state = new SimpleBlockState(index++, type, stateProperties);
            states.add(state);
            propertyMappedStates.put(stateProperties, state);
        }
        this.states = states.build();

        for (SimpleBlockState state : this.states) {
            state.buildChangeTable(propertyMappedStates);
        }
    }

    public BlockStateRegistry(BlockType type, BlockProperty<?>... properties) {
        this(type, Arrays.asList(properties));
    }

    protected <K, V> ImmutableMap<K, V> mapOf(Collection<K> keys, Collection<V> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Cannot map " + keys.size() + " keys to " + values.size() + " values!");
        }
        ImmutableMap.Builder<K, V> map = ImmutableMap.builder();
        Iterator<V> valueIt = values.iterator();
        for(K key : keys) {
            map.put(key, valueIt.next());
        }
        return map.build();
    }

    public BlockState getDefaultState() {
        return states.get(0);
    }

    public BlockType getBlockType() {
        return type;
    }

    public ImmutableList<SimpleBlockState> getStates() {
        return states;
    }

    public SimpleBlockState getState(int id) {
        return states.get(id);
    }

    public BlockProperty<?> getProperty(String name) {
        return nameBakedProperties.get(name);
    }

    public static class SimpleBlockState implements BlockState {
        private final int index;
        private final BlockType blockType;
        private final ImmutableMap<BlockProperty<?>, Comparable<?>> properties;
        private ImmutableTable<BlockProperty<?>, Comparable<?>, SimpleBlockState> changeTable;

        public SimpleBlockState(int index, BlockType blockType, ImmutableMap<BlockProperty<?>, Comparable<?>> properties) {
            this.index = index;
            this.blockType = blockType;
            this.properties = properties;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Comparable<T>> T get(BlockProperty<T> property) {
            return (T) properties.get(property);
        }

        @Override
        public <T extends Comparable<T>> BlockState with(BlockProperty<T> property, T value) {
            SimpleBlockState state = changeTable.get(property, value);
            if (state != null) {
                return state;
            } else {
                Comparable<?> currentValue = properties.get(property);

                if (currentValue == value) {
                    return this;
                } else if (currentValue == null) {
                    throw new IllegalArgumentException("Illegal property change: cannot find property " + property.getName());
                } else {
                    throw new IllegalArgumentException("Illegal value " + value + ", possible values: " + property.getPossibleValues());
                }
            }
        }

        @Override
        public ImmutableMap<BlockProperty<?>, Comparable<?>> getProperties() {
            return properties;
        }

        @Override
        public BlockType getBlockType() {
            return blockType;
        }

        @Override
        public int getId() {
            return index;
        }

        public void buildChangeTable(Map<Map<BlockProperty<?>, Comparable<?>>, SimpleBlockState> states) {
            if (changeTable == null) {
                throw new IllegalStateException("Already initialized!");
            }
            Table<BlockProperty<?>, Comparable<?>, SimpleBlockState> table = HashBasedTable.create();

            this.properties.entrySet().forEach(e -> {
                BlockProperty<?> currentProprierty = e.getKey();
                Comparable<?> currentValue = e.getValue();
                for (Comparable<?> value : currentProprierty.getPossibleValues()) {
                    if (value != currentValue) {
                        Map<BlockProperty<?>, Comparable<?>> map = new HashMap<>(this.properties);
                        map.put(currentProprierty, value);
                        table.put(currentProprierty, value, states.get(map));
                    }
                }
            });

            this.changeTable = ImmutableTable.copyOf(table);
        }

        @Override
        public boolean equals(Object other) {
            return other == this;
        }

        @Override
        public int hashCode() {
            return properties.hashCode();
        }
    }

    public static BlockStateRegistry of(BlockType blockType, BlockProperty<?>... properties) {
        return new BlockStateRegistry(blockType, properties);
    }
}
