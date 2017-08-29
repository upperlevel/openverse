package xyz.upperlevel.openverse.resource;

import lombok.Getter;

import java.util.Locale;

@Getter
public class Identifier<T> {
    private final String id;
    private final T entry;

    public Identifier(String id, T entry) {
        this.id = id.toLowerCase(Locale.ENGLISH);
        this.entry = entry;
    }
}
