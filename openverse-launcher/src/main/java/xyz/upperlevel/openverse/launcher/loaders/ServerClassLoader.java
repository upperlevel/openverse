package xyz.upperlevel.openverse.launcher.loaders;

import java.net.URL;
import java.net.URLClassLoader;

public class ServerClassLoader extends URLClassLoader {
    public ServerClassLoader(URL serverJar) {
        super(new URL[]{serverJar});
    }
}
