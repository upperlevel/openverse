package xyz.upperlevel.openverse.client.world.entity.input;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.world.entity.input.LivingEntityInput;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.key.Key;

public class PlayerEntityInput implements LivingEntityInput, Listener {
    private static final float SPEED = 0.5f;
    private static final float SENSIBILITY = 0.5f;

    @Getter
    private final Window window;

    @Getter
    private float strafe, up, forward, yaw, pitch;

    private double lastCursorX;
    private double lastCursorY;
    private float cumulativeYaw = 0f;
    private float cumulativePitch = 0f;

    public PlayerEntityInput(Window window) {
        this.window = window;
        window.getEventManager().register(this);
    }

    @Override
    public void onTick() {
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
            //all enabled or all disabled
            return 0;
        } else return enPos ? speed : -speed;
    }

    @EventHandler
    public void onCursorMove(CursorMoveEvent e) {
        // Openverse.logger().info(" Detected a cursor movement: " + e.getX() + " " + e.getY());
        cumulativeYaw   += (float) (e.getX() - lastCursorX) * SENSIBILITY;
        cumulativePitch += (float) (e.getY() - lastCursorY) * SENSIBILITY;
        this.lastCursorX = e.getX();
        this.lastCursorY = e.getY();
        // Openverse.logger().info(" Player rotate look of: " + dx + " " + dy);
    }
}
