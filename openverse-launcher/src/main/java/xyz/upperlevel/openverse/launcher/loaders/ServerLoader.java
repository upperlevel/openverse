package xyz.upperlevel.openverse.launcher.loaders;

import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.server.OpenverseServer;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;

public class ServerLoader {
    private static final String PATH_TO_SERVER = "bin/Server.jar";
    private static final String OPENVERSE_SERVER_PATH = "xyz.upperlevel.openverse.server.OpenverseServer";
    private ServerClassLoader loader;


    public void load() {
        try {
            loader = new ServerClassLoader(new File(PATH_TO_SERVER).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Cannot load url: \"" + PATH_TO_SERVER + "\"", e);
        }
    }

    @SuppressWarnings("unchecked")
    public OpenverseServer createServer(Server connection) {
        Class<OpenverseServer> serverClass;
        try {
            serverClass = (Class<OpenverseServer>) loader.loadClass(OPENVERSE_SERVER_PATH);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find OpenverseServer class!", e);
        }
        Constructor<OpenverseServer> constructor;
        try {
            constructor = serverClass.getConstructor(Client.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Cannot find OpenverseServer constructor!", e);
        }
        try {
            return constructor.newInstance(connection);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot initialize OpenverseServer!", e);
        }
    }
}
