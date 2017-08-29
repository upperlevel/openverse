package xyz.upperlevel.openverse.launcher.loaders;

import xyz.upperlevel.ulge.game.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientWrapper {
    private final Object handle;

    public ClientWrapper(Object handle) {
        this.handle = handle;
    }

    public void join(Stage stage) {
        try {
            Method method = handle.getClass().getDeclaredMethod("join", Stage.class);
            method.invoke(handle, stage);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
