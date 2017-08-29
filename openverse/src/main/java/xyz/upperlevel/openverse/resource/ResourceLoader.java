package xyz.upperlevel.openverse.resource;

import java.io.File;

public interface ResourceLoader<R> {
    Identifier<R> load(File file);
}
