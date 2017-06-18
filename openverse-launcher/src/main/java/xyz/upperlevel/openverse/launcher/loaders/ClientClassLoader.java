package xyz.upperlevel.openverse.launcher.loaders;

import java.net.URL;
import java.net.URLClassLoader;

public class ClientClassLoader extends URLClassLoader {
    public ClientClassLoader(URL clientJar) {
        super(new URL[]{clientJar});
    }
}
