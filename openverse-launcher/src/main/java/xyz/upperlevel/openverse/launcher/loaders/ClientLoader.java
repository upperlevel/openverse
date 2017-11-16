package xyz.upperlevel.openverse.launcher.loaders;

import xyz.upperlevel.hermes.client.Client;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;

public class ClientLoader {
    private static final String PATH_TO_CLIENT = "client/openverse-client.jar";
    private static final String OPENVERSE_CLIENT_PATH = "xyz.upperlevel.openverse.client.OpenverseClient";
    private ProxyClassLoader loader;


    public void load() {
        try {
            loader = new ProxyClassLoader(new File(PATH_TO_CLIENT).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Cannot load url: \"" + PATH_TO_CLIENT + "\"", e);
        }
    }

    @SuppressWarnings("unchecked")
    public ClientWrapper createClient(Client connection, PrintStream writer) {
        Class<?> clientClass;
        try {
             clientClass = loader.loadClass(OPENVERSE_CLIENT_PATH);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find OpenverseClient class!", e);
        }
        Constructor<?> constructor;
        try {
            constructor = clientClass.getConstructor(Client.class, PrintStream.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Cannot find OpenverseClient constructor!", e);
        }
        try {
            Object hnd = constructor.newInstance(connection, writer);
            return new ClientWrapper(hnd);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot initialize OpenverseClient!", e);
        }
    }
}
