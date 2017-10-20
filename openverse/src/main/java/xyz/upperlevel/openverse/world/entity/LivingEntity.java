package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.input.LivingEntityDriver;

@Getter
@Setter
public class LivingEntity extends Entity {
    private LivingEntityDriver driver = LivingEntityDriver.FREEZE;
    //TODO add life

    public LivingEntity(EntityType type, Location spawn) {
        super(type, spawn);
    }

    public void travel(float strafe, float up, float forward) {
        setRelativeVelocity(strafe, up, forward);
        move();
    }

    @Override
    public void onTick() {
        super.onTick();
        driver.onTick();
        rotate(driver.getYaw(), driver.getPitch());
        travel(driver.getStrafe(), driver.getUp(), driver.getForward());
    }
}
