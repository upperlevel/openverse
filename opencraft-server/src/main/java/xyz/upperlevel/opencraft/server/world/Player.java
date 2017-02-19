package xyz.upperlevel.opencraft.server.world;

import xyz.upperlevel.opencraft.server.network.SingleplayerServer;

public class Player {

    private PlayerLocation location;

    public Player() {
        location = new PlayerLocation(this);
    }

    public PlayerLocation getLocation() {
        return location;
    }
}