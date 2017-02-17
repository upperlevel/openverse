package xyz.upperlevel.opencraft.client.controller;

import lombok.Getter;
import org.joml.Vector3d;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.action.Action;
import xyz.upperlevel.ulge.window.event.key.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicEntityController implements Controller {

    @Getter
    private final List<ControllableEntity> controllingEntities = new ArrayList<>();

    @Getter
    private double lastScreenX, lastScreenY;

    public BasicEntityController() {
    }

    public void move(double x, double y, double z) {
        controllingEntities.forEach(entity -> entity.teleport(x, y, z));
    }

    public void move(Vector3d movement) {
        move(movement.x, movement.y, movement.z);
    }

    public void rotate(float x, float y) {
        controllingEntities.forEach(entity -> entity.teleport(x, y));
    }

    @Override
    public void onCall(Window window, double x, double y) {
        double movX = lastScreenX - x;
        double movY = y - lastScreenY;
        rotate((float) movX, (float) movY);
        lastScreenX = x;
        lastScreenY = y;
    }

    private static final Map<Key, Vector3d> movementsPerKey = new HashMap<Key, Vector3d>() {{
        put(Key.W,          new Vector3d(0, 0, 1));
        put(Key.A,          new Vector3d(-1, 0, 0));
        put(Key.S,          new Vector3d(0, 0, -1));
        put(Key.D,          new Vector3d(1, 0, 0));
        put(Key.SPACE,      new Vector3d(0, 1, 0));
        put(Key.LEFT_SHIFT, new Vector3d(0, -1, 0));
    }};

    public void move(Key key) {
        Vector3d mov = movementsPerKey.get(key);
        if (mov != null)
            move(mov);
    }

    @Override
    public void onCall(Window window, Key key, Action action) {
        if (action == Action.PRESS)
            move(key);
    }
}
