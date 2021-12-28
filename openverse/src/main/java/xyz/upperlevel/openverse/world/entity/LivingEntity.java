package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.input.LivingEntityDriver;

public class LivingEntity extends Entity {
    @Getter
    @Setter
    private LivingEntityDriver driver = LivingEntityDriver.FREEZE;
    //TODO add life

    public LivingEntity(OpenverseProxy module, EntityType type, Location spawn) {
        super(module, type, spawn);
    }

    public void travel(float strafe, float up, float forward) {
        setRelativeVelocity(strafe, up, forward);
        addGravity();
        move();
    }

    @Override
    public void updateMovement() {
        driver.onTick();
        rotate(driver.getYaw(), driver.getPitch());
        travel(driver.getStrafe(), driver.getUp(), driver.getForward());
    }
}
