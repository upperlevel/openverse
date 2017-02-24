package xyz.upperlevel.opencraft.server.world;

public class Player {

    private PlayerLocation location;

    public Player() {
        location = new PlayerLocation(this);
    }

    public PlayerLocation getLocation() {
        return location;
    }
}