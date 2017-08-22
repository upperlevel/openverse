package xyz.upperlevel.openverse.launcher.loaders;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class ProxyClassLoader extends URLClassLoader {
    public ProxyClassLoader(URL proxyFile) {
        super(new URL[]{ proxyFile });
        try {
            if(!Paths.get(proxyFile.toURI()).toFile().exists()) {
                throw new IllegalArgumentException("Proxy file absent! " + proxyFile);
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid uri syntax " + proxyFile, e);
        }

    }
}