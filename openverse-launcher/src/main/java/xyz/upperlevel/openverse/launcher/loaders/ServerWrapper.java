package xyz.upperlevel.openverse.launcher.loaders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

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

    public void executeCommand(String line) {
        try {
            handle.getClass().getDeclaredMethod("executeCommand", String.class).invoke(handle, line);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> tabComplete(String line) {
        try {
            return (List<String>) handle.getClass().getDeclaredMethod("tabComplete", String.class).invoke(handle, line);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void close() {
        try {
            Method method = handle.getClass().getDeclaredMethod("stop");
            method.invoke(handle);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
