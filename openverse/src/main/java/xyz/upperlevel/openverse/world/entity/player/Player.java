package xyz.upperlevel.openverse.world.entity.player;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.EntityType;
import xyz.upperlevel.openverse.world.entity.LivingEntity;

@Getter
public class Player extends LivingEntity {
    public static final Type TYPE = new Type();
    private final String name;
    @Getter
    @Setter
    private Connection connection;

    public Player(Location loc, String name) {
        super(TYPE, loc);
        this.name = name;
        setSize(0.6f, 1.8f);
    }

    public EntityType getType() {
        return TYPE;
    }

    public static class Type extends EntityType {

        public Type() {
            super("player");
        }

        @Override
        public Player spawn(Location loc) {
            throw new IllegalStateException("Cannot spawn player");
        }
    }
}
