package xyz.upperlevel.openverse.launcher.loaders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientProxyWrapper extends ProxyWrapper {
    private final Method render;


    public ClientProxyWrapper(Object handle) {
        super(handle);
        try {
            render = handle.getClass().getMethod("render");
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Cannot find render method!", e);
        }
    }

    public void render() {
        try {
            render.invoke(handle);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Error while accessing a public method", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Exception while rendering", e.getCause());
        }
    }
}
