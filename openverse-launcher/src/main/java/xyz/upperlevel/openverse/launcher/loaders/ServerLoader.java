package xyz.upperlevel.openverse.launcher.loaders;

import xyz.upperlevel.hermes.server.Server;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;

public class ServerLoader {
    private static final String PATH_TO_SERVER = "server/openverse-server.jar";
    private static final String OPENVERSE_SERVER_PATH = "xyz.upperlevel.openverse.server.OpenverseServer";
    private ProxyClassLoader loader;


    public void load() {
        try {
            loader = new ProxyClassLoader(new File(PATH_TO_SERVER).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Cannot load url: \"" + PATH_TO_SERVER + "\"", e);
        }
    }

    @SuppressWarnings("unchecked")
    public ServerWrapper createServer(Server connection, PrintStream writer) {
        Class<?> serverClass;
        try {
            serverClass = loader.loadClass(OPENVERSE_SERVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find OpenverseServer class!", e);
        }
        Constructor<?> constructor;
        try {
            constructor = serverClass.getConstructor(Server.class, PrintStream.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Cannot find OpenverseServer constructor!", e);
        }
        try {
            return new ServerWrapper(constructor.newInstance(connection, writer));
        } catch (Exception e) {
            throw new IllegalStateException("Cannot initialize OpenverseServer!", e);
        }
    }
}
