package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.input.LivingEntityInput;

public class LivingEntity extends Entity {
    @Getter
    @Setter
    private LivingEntityInput input = LivingEntityInput.FREEZE;
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
        input.onTick();
        rotate(input.getYaw(), input.getPitch());
        travel(input.getStrafe(), input.getUp(), input.getForward());
    }
}
