package xyz.upperlevel.openverse.profiler;

import lombok.Getter;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ProfileSession implements Closeable {
    @Getter
    private final String name;
    private int callTimes = 0;
    private long cumulativeNanos = 0;
    private long lastStartNanos = -1;


    public ProfileSession(String name) {
        this.name = name;
    }

    public ProfileSession start() {
        lastStartNanos = System.nanoTime();
        return this;
    }

    public void stop() {
        assert lastStartNanos > 0;
        long endNanos = System.nanoTime();
        cumulativeNanos += (endNanos - lastStartNanos);
        callTimes++;
        lastStartNanos = -1;
    }

    @Override
    public void close() throws IOException {
        stop();
    }

    public int getCallCount() {
        return callTimes;
    }

    public long getAverageNanos() {
        return cumulativeNanos / callTimes;
    }

    public long getAverageTime(TimeUnit unit) {
        return unit.convert(getAverageNanos(), TimeUnit.NANOSECONDS);
    }

    public void reset() {
        this.cumulativeNanos = 0;
        this.callTimes = 0;
    }
}
