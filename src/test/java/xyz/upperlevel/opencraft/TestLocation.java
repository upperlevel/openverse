package xyz.upperlevel.opencraft;

import org.junit.Test;
import xyz.upperlevel.opencraft.world.Location;
import xyz.upperlevel.opencraft.world.World;

public class TestLocation {

    private static final World W = new World();
    private static final Location
            LOC1 = new Location(W, 0, 2, 0),
            LOC2 = new Location(W, 0, 2, 0),
            LOC3 = new Location(W, 0, 2.1, 0);

    @Test
    public void checkHashCode() {
        assert LOC1.hashCode() == LOC2.hashCode() : "Locations hashcode should be equal";
    }

    @Test
    public void checkDiffHashCode() {
        assert LOC1.hashCode() != LOC3.hashCode() : "Locations hashcode should be different";
    }

    @Test
    public void checkEquals() {
        assert LOC1.equals(LOC2) : "Locations should be equals";
    }

    @Test
    public void checkDiffEquals() {
        assert !LOC1.equals(LOC3) : "Locations should be different";
    }
}