package xyz.upperlevel.opencraft;

import org.joml.Vector3f;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class TestCameraDot {

    public static final Vector3f NORM_DIR = new Vector3f(0, 1, 0);

    private float testDot() {
        Vector3f cameraPos = new Vector3f(10, 0.2f, 2);
        Vector3f objectPos = new Vector3f(2, 0, 2);
        Vector3f cameraDir = new Vector3f();
        cameraPos.sub(objectPos, cameraDir);
        return NORM_DIR.dot(cameraDir);
    }

    @Test
    public void checkDot() {
        float res = testDot();
        System.out.println("Result canRender dot: " + res);
        assert res > 0 : "Dot doesn't work.";
    }

    @Test
    public void checkDotTime() {
        long startAt = System.nanoTime();
        long totTime = 0;
        for (int i = 0; i < 100; i++) {
            testDot();
            totTime += (System.nanoTime() - startAt);
        }
        totTime /= 100;
        out.println("Time computed for dot (Millis): " + (TimeUnit.NANOSECONDS.toMillis(totTime)));
        out.println("Time computed for dot (Nanos): " + totTime);
        assert totTime < TimeUnit.MILLISECONDS.toNanos(1) : "Too much time token.";
    }
}
