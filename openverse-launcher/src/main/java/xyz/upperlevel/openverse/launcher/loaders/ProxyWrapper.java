package xyz.upperlevel.openverse.launcher.loaders;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;

public class ProxyWrapper {
    protected final Object handle;

    public ProxyWrapper(Object handle) {
        this.handle = handle;
        System.out.println("SUPER!");
    }

    public void loadResources() {
        try {
            Object resManager = handle.getClass().getMethod("getResources").invoke(handle);
            resManager.getClass().getMethod("load").invoke(resManager);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Error while accessing resource system", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Error while loading resources", e.getCause());
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Cannot find getResourceManager() in proxy", e);
        }
    }
}
