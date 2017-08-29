package xyz.upperlevel.openverse.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Registry<E> {
    private final Map<String, Identifier<E>> identifiers = new HashMap<>();
    private final Map<String, E> entries = new HashMap<>();

    /**
     * Registers both entry and its {@link Identifier} object.
     * @param id the id
     * @param entry the entry
     */
    public void register(String id, E entry) {
        id = id.toLowerCase(Locale.ENGLISH);
        entries.put(id, entry);
        identifiers.put(id, new Identifier<>(id, entry));
    }

    /**
     * Registers both {@link Identifier} object and its entry.
     * @param identifier the identifier object
     */
    public void register(Identifier<E> identifier) {
        register(identifier.getId(), identifier.getEntry());
    }

    /**
     * Unregisters both entry and {@link Identifier} object.
     * @param id the id
     * @return {@code true} if at least one of them has been removed, otherwise {@code false}
     */
    public boolean unregister(String id) {
        return identifiers.remove(id) != null || entries.remove(id) != null;
    }

    /**
     * Gets an {@link Identifier} with the fetcher id.
     */
    public Identifier<E> identifier(String id) {
        return identifiers.get(id.toLowerCase(Locale.ENGLISH));
    }

    /**
     * Gets a complete list of all {@link Identifier} objects.
     */
    public Collection<Identifier<E>> identifiers() {
        return identifiers.values();
    }

    /**
     * Gets an entry by its id registered in this registry.
     */
    public E entry(String id) {
        return entries.get(id.toLowerCase(Locale.ENGLISH));
    }

    /**
     * Gets a list of all entries registered.
     */
    public Collection<E> entries() {
        return entries.values();
    }

    public void clear() {
        identifiers.clear();
    }
}
