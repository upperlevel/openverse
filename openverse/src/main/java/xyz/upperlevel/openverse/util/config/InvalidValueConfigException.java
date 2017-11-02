package xyz.upperlevel.openverse.util.config;

import lombok.Getter;

@Getter
public class InvalidValueConfigException extends InvalidConfigurationException {
    private final String key;
    private final Object value;
    private final String expected;


    public InvalidValueConfigException(String key, Object value, String expected, String... localizers) {
        super("Invalid value in '" + key + "' (found '" + value.getClass().getSimpleName() + "', expected '" + expected + "')", localizers);
        this.key = key;
        this.value = value;
        this.expected = expected;
    }
}
