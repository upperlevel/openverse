package xyz.upperlevel.openverse.world.chunk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Collections.emptyIterator;

public class ChunkMap<T> implements Iterable<T> {
    //TODO: use a primitive-optimized Map (or recreate a map implementation from scratch)
    private Map<Long, Map<Integer, T>> pillars = new HashMap<>();


    public boolean isEmpty() {
        return pillars.isEmpty();
    }

    public T put(ChunkLocation chunk, T t) {
        Map<Integer, T> pillar = pillars.computeIfAbsent(chunk.getPillarId(), l -> new HashMap<>());
        return pillar.put(chunk.y, t);
    }

    public T put(int x, int y, int z, T t) {
        Map<Integer, T> pillar = pillars.computeIfAbsent(ChunkPillar.hash(x, z), l -> new HashMap<>());
        return pillar.put(y, t);
    }

    public T get(ChunkLocation chunk) {
        Map<Integer, T> pillar = pillars.get(chunk.getPillarId());
        return pillar == null ? null : pillar.get(chunk.y);
    }

    public T get(int x, int y, int z) {
        Map<Integer, T> pillar = pillars.get(ChunkPillar.hash(x, z));
        return pillar == null ? null : pillar.get(y);
    }

    public boolean containsKey(ChunkLocation chunk) {
        Map<Integer, T> pillar = pillars.get(chunk.getPillarId());
        return pillar != null && pillar.containsKey(chunk.y);
    }

    public boolean containsKey(int x, int y, int z) {
        Map<Integer, T> pillar = pillars.get(ChunkPillar.hash(x, z));
        return pillar != null && pillar.containsKey(y);
    }

    public T remove(ChunkLocation chunk) {
        long pillarId = chunk.getPillarId();
        Map<Integer, T> pillar = pillars.get(pillarId);
        if (pillar == null) {
            return null;
        }
        T res = pillar.remove(chunk.y);
        if (pillar.isEmpty()) {
            pillars.remove(pillarId);
        }
        return res;
    }

    public T remove(int x, int y, int z) {
        long pillarId = ChunkPillar.hash(x, z);
        Map<Integer, T> pillar = pillars.get(pillarId);
        if (pillar == null) {
            return null;
        }
        T res = pillar.remove(y);
        if (pillar.isEmpty()) {
            pillars.remove(pillarId);
        }
        return res;
    }

    public void forEach(Consumer<? super T> consumer) {
        for (Map<Integer, T> pillar : pillars.values()) {
            pillar.values().forEach(consumer);
        }
    }

    public void computeIfPresent(int x, int y, int z, CoordBiFunction<T, T> provider) {
        long pillarId = ChunkPillar.hash(x, z);
        Map<Integer, T> pillar = pillars.get(pillarId);
        if (pillar == null) {
            return;
        }
        T found = pillar.get(y);
        if (found == null) {
            return;
        }
        T computed = provider.apply(x, y, z, found);
        if (computed == found) {
            return;
        }
        if (computed != null) {
            pillar.put(y, computed);
        } else {
            pillar.remove(y);
            if (pillar.isEmpty()) {
                pillars.remove(pillarId);
            }
        }
    }

    public void computeIfPresent(ChunkLocation loc, BiFunction<ChunkLocation, T, T> provider) {
        long pillarId = loc.getPillarId();
        Map<Integer, T> pillar = pillars.get(pillarId);
        if (pillar == null) {
            return;
        }
        T found = pillar.get(loc.y);
        if (found == null) {
            return;
        }
        T computed = provider.apply(loc, found);
        if (computed == found) {
            return;
        }
        if (computed != null) {
            pillar.put(loc.y, computed);
        } else {
            pillar.remove(loc.y);
            if (pillar.isEmpty()) {
                pillars.remove(pillarId);
            }
        }
    }

    public void computeIfAbsent(int x, int y, int z, CoordFunction<T> provider) {
        long pillarId = ChunkPillar.hash(x, z);
        Map<Integer, T> pillar = pillars.get(pillarId);
        if (pillar == null) {
            T computed = provider.apply(x, y, z);
            if (computed == null) {
                return;
            }
            pillar = new HashMap<>();
            pillar.put(y, computed);
            pillars.put(pillarId, pillar);
        } else {
            if (pillar.containsKey(y)) {
                return;
            }
            T computed = provider.apply(x, y, z);
            if (computed == null) {
                return;
            }
            pillar.put(y, computed);
        }
    }

    public void computeIfAbsent(ChunkLocation loc, Function<ChunkLocation, T> provider) {
        long pillarId = loc.getPillarId();
        Map<Integer, T> pillar = pillars.get(pillarId);
        if (pillar == null) {
            T computed = provider.apply(loc);
            if (computed == null) {
                return;
            }
            pillar = new HashMap<>();
            pillar.put(loc.y, computed);
            pillars.put(pillarId, pillar);
        } else {
            if (pillar.containsKey(loc.y)) {
                return;
            }
            T computed = provider.apply(loc);
            if (computed == null) {
                return;
            }
            pillar.put(loc.y, computed);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    public class Itr implements Iterator<T> {
        private Iterator<Map<Integer, T>> pillarIterator;
        private Iterator<T> chunkIterator;

        public Itr() {
            pillarIterator = pillars.values().iterator();
            chunkIterator = pillarIterator.hasNext() ? pillarIterator.next().values().iterator() : emptyIterator();
        }

        @Override
        public boolean hasNext() {
            return chunkIterator.hasNext() || pillarIterator.hasNext();
        }

        @Override
        public T next() {
            if (!chunkIterator.hasNext()) {
                chunkIterator = pillarIterator.next().values().iterator();
            }
            // This operation should be possible given the assumption that no empty pillar is stored
            return chunkIterator.next();
        }
    }

    @FunctionalInterface
    public interface CoordFunction<T> {
        T apply(int x, int y, int z);
    }

    @FunctionalInterface
    public interface CoordBiFunction<X, T> {
        T apply(int x, int y, int z, X arg);
    }
}
