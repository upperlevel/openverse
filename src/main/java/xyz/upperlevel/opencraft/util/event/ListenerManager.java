package xyz.upperlevel.opencraft.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListenerManager {

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class EventFetcher {

        private final Class<? extends Event> ev;
        private final Method mth;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class RegisteredListener {

        private final Listener listener;
        private final List<EventFetcher> evs = new ArrayList<>();

        private List<Method> getMethods(Class<? extends Event> ev) {
            return evs.stream()
                    .filter(evb -> evb.ev == ev)
                    .map(evb -> evb.mth)
                    .collect(Collectors.toList());
        }
    }

    private final Map<Listener, RegisteredListener> listeners = new HashMap<>();

    public ListenerManager() {
    }

    public ListenerManager register(Listener listener) {
        RegisteredListener res = new RegisteredListener(listener);
        for (Method m : listener.getClass().getDeclaredMethods()) {
            // checks if the method can represent an event
            EventFunction ef = m.getDeclaredAnnotation(EventFunction.class);
            if (ef == null)
                continue;
            Class<?>[] par = m.getParameterTypes();
            if (par.length != 1)
                continue;
            if (!Event.class.isAssignableFrom(par[0]))
                continue;
            Class<? extends Event> ev = par[0].asSubclass(Event.class);
            // registers the event
            m.setAccessible(true);
            res.evs.add(new EventFetcher(ev, m));
        }
        listeners.put(listener, res);
        return this;
    }

    public boolean isRegistered(Listener listener) {
        return listeners.containsKey(listener);
    }

    private ListenerManager call(Event event, RegisteredListener rl) {
        rl.getMethods(event.getClass()).forEach(mth -> {
            try {
                mth.invoke(rl.listener, event);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("The listener " + rl.listener.getClass().getName() + " thrown an error while executing.", e);
            } catch (IllegalAccessException ignored) {
            }
        });
        return this;
    }

    public ListenerManager call(Event event, Listener listener) {
        if (!isRegistered(listener))
            throw new IllegalArgumentException("The listener " + listener.getClass().getName() + " is not registered.");
        return call(event, listeners.get(listener));
    }

    public ListenerManager call(Event event) {
        listeners.values().forEach(rl -> call(event, rl));
        return this;
    }

    public ListenerManager unregister(Listener listener) {
        listeners.remove(listener);
        return this;
    }

    public ListenerManager clear() {
        listeners.clear();
        return this;
    }
}