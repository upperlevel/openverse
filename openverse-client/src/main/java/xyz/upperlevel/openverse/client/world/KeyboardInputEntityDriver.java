package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.world.entity.input.LivingEntityDriver;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.key.Key;

@Getter
public class KeyboardInputEntityDriver implements LivingEntityDriver, Listener {
    private static final float SPEED = 0.5f;
    private static final float SENSIBILITY = 0.5f;

    private final Window window;
    private float strafe, up, forward, yaw, pitch;

    private double lastCursorX, lastCursorY;
    private float cumulativeYaw, cumulativePitch;

    public KeyboardInputEntityDriver(Window window) {
        this.window = window;
        window.getEventManager().register(this);
    }

    @Override
    public void onTick() {
        if (!OpenverseClient.get().isCaptureInput()) {
            strafe = 0;
            up = 0;
            forward = 0;
            yaw = 0;
            pitch = 0;
            return;
        }
        strafe  = getMovement(Key.D,     Key.A,          SPEED);
        up      = getMovement(Key.SPACE, Key.LEFT_SHIFT, SPEED);
        forward = getMovement(Key.W,     Key.S,          SPEED);

        yaw   = cumulativeYaw;
        pitch = cumulativePitch;

        cumulativeYaw = 0f;
        cumulativePitch = 0f;
    }

    protected float getMovement(Key pos, Key neg, float speed) {
        boolean enPos = window.getKey(pos);
        boolean enNeg = window.getKey(neg);

        if (enPos == enNeg) {
            // All enabled or all disabled
            return 0;
        } else
            return enPos ? speed : -speed;
    }

    @EventHandler
    public void onCursorMove(CursorMoveEvent e) {
        if (OpenverseClient.get().isCaptureInput()) {
            cumulativeYaw += (float) (e.getX() - lastCursorX) * SENSIBILITY;
            cumulativePitch += (float) (e.getY() - lastCursorY) * SENSIBILITY;
        }

        lastCursorX = e.getX();
        lastCursorY = e.getY();
    }
}
