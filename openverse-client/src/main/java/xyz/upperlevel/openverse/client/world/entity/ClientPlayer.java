package xyz.upperlevel.openverse.client.world.entity;

import lombok.Getter;
import org.joml.Vector3f;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.util.math.CameraUtil;

public class ClientPlayer extends ClientEntity implements Player {

    public static float xySensibility = 1.0f, ySensibility = 1.0f, mouseSensibility = 0.5f;

    @Getter
    private String name;

    @Getter
    private final Connection connection;


    public ClientPlayer(String name, Connection connection) {
        super(TYPE);
        this.name = name;
        this.connection = connection;
    }

    public void onInput(float x, float y, float z, float mouseX, float mouseY) {
        Vector3f forward = CameraUtil.getForward((float)getYaw(), (float)getPitch());
        x *= (forward.x * xySensibility);
        y *= (forward.y * ySensibility);
        z *= (forward.z * xySensibility);
        setVelocity(getVelocity().add(x, y, z));

        mouseX *= mouseSensibility;
        mouseY *= mouseSensibility;

        setRotation(getYaw() + mouseX, getPitch() + mouseY);
    }

    @Override
    public void render() {
        //TODO: render player
    }
}
