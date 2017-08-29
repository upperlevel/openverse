package xyz.upperlevel.openverse.util.config;

import lombok.Getter;

@Getter
public class RequiredPropertyNotFoundException extends InvalidConfigurationException {
    private final String property;

    public RequiredPropertyNotFoundException(String key, String... localizers) {
        super("Cannot find property \"" + key + "\"", localizers);
        this.property = key;
    }
}
