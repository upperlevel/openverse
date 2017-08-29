package xyz.upperlevel.openverse.launcher.loaders;

import xyz.upperlevel.ulge.game.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerWrapper {
    private final Object handle;

    public ServerWrapper(Object handle) {
        this.handle = handle;
    }

    public void join() {
        try {
            Method method = handle.getClass().getDeclaredMethod("join");
            method.invoke(handle);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
