package xyz.upperlevel.opencraft;

import org.junit.Test;
import xyz.upperlevel.opencraft.world.BlockComponent;
import xyz.upperlevel.opencraft.world.BlockFace;
import xyz.upperlevel.opencraft.world.BlockFacePosition;
import xyz.upperlevel.opencraft.world.Zone3f;

import static java.lang.System.out;

public class BlockCompTest {

    /**
     * Tests if the faces get splitter correctly from
     * their block component.
     */
    @Test
    public void testFaceSplitter() {
        // instantiate default block component
        Zone3f zone = new Zone3f(
                0, 0, 0,
                1f, 1f, 1f
        );
        BlockComponent comp = new BlockComponent(zone);

        // gets some faces and checks if the obtained value correspond to expected ones

        // UP FACE
        out.println("----------");

        BlockFace face;

        face = comp.getFace(BlockFacePosition.UP);
        zone = face.getZone();
        out.println("up first position: (" + zone.getMinX() + "," + zone.getMinY() + "," + zone.getMinZ() + ")");
        out.println("up second position: (" + zone.getMaxX() + "," + zone.getMaxY() + "," + zone.getMaxZ() + ")");
        assert zone.getMinX() == 0. && zone.getMinY() == 1. && zone.getMinZ() == 0. &&
                zone.getMaxX() == 1. && zone.getMaxY() == 1. && zone.getMaxZ() == 1. : "up face values does not correspond to expected ones";

        // LEFT FACE
        out.println("----------");

        face = comp.getFace(BlockFacePosition.LEFT);
        zone = face.getZone();
        out.println("left first position: (" + zone.getMinX() + "," + zone.getMinY() + "," + zone.getMinZ() + ")");
        out.println("left second position: (" + zone.getMaxX() + "," + zone.getMaxY() + "," + zone.getMaxZ() + ")");
        assert zone.getMinX() == 0. && zone.getMinY() == 0. && zone.getMinZ() == 0. &&
                zone.getMaxX() == 0. && zone.getMaxY() == 1. && zone.getMaxZ() == 1. : "left face values does not correspond to expected ones";

        // FORWARD FACE
        out.println("----------");

        face = comp.getFace(BlockFacePosition.FORWARD);
        zone = face.getZone();
        out.println("forward first position: (" + zone.getMinX() + "," + zone.getMinY() + "," + zone.getMinZ() + ")");
        out.println("forward second position: (" + zone.getMaxX() + "," + zone.getMaxY() + "," + zone.getMaxZ() + ")");
        assert zone.getMinX() == 0. && zone.getMinY() == 0. && zone.getMinZ() == 0. &&
                zone.getMaxX() == 1. && zone.getMaxY() == 1. && zone.getMaxZ() == 0. : "forward face values does not correspond to expected ones";

        out.println("----------");
        out.println("FACE SPLITTING TEST ENDED SUCCESSFULLY");
    }
}
