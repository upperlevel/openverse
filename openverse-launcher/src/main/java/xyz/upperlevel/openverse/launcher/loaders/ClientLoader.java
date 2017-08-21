package xyz.upperlevel.openverse.launcher.loaders;

import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.launcher.ProxyWrapper;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;

public class ClientLoader {
    private static final String PATH_TO_CLIENT = "bin/Client.jar";
    private static final String OPENVERSE_CLIENT_PATH = "xyz.upperlevel.openverse.client.OpenverseClient";
    private ClientClassLoader loader;


    public void load() {
        try {
            loader = new ClientClassLoader(new File(PATH_TO_CLIENT).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Cannot load url: \"" + PATH_TO_CLIENT + "\"", e);
        }
    }

    @SuppressWarnings("unchecked")
    public ProxyWrapper createClient(Client connection) {
        Class<?> clientClass;
        try {
             clientClass = loader.loadClass(OPENVERSE_CLIENT_PATH);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find OpenverseClient class!", e);
        }
        Constructor<?> constructor;
        try {
            constructor = clientClass.getConstructor(Client.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Cannot find OpenverseClient constructor!", e);
        }
        try {
            return new ProxyWrapper(constructor.newInstance(connection));
        } catch (Exception e) {
            throw new IllegalStateException("Cannot initialize OpenverseClient!", e);
        }
    }
}
