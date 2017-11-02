package xyz.upperlevel.openverse.util;

public class Box<T> {
    private T obj;

    public Box(T init) {
        this.obj = init;
    }

    public Box() {
        this.obj = null;
    }

    public T get() {
        return obj;
    }

    public void set(T obj) {
        this.obj = obj;
    }
}
