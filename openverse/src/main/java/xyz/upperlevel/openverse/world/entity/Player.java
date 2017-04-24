package xyz.upperlevel.openverse.world.entity;

import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;

public class Player extends Entity {

    public static EntityType TYPE = new EntityType("player");


    private Location loc;

    public Player(Location firstLoc) {
        super(TYPE);
        this.loc = firstLoc;
    }

    public Location getLoc() {
        return new Location(loc);
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}
