package xyz.upperlevel.openverse.util;

import org.junit.Assert;
import org.junit.Test;

public class AabbTest {
    @Test
    public void checkAabb2d() {
        Aabb2d aabb = new Aabb2d();
        aabb.minX = 0;
        aabb.minY = 0;
        aabb.maxX = 1;
        aabb.maxY = 1;

        // Test points
        Assert.assertTrue(aabb.testPoint(1, 1));
        Assert.assertTrue(aabb.testPoint(0.5, 0.5));
        Assert.assertTrue(aabb.testPoint(0, 0));
        Assert.assertFalse(aabb.testPoint(-1, -1));

        // Test equal aabb
        Aabb2d test = new Aabb2d();
        test.minX = 0;
        test.minY = 0;
        test.maxX = 1;
        test.maxY = 1;

        Assert.assertTrue(aabb.isColliding(test));
        Assert.assertTrue(aabb.isInside(test));

        // Test colliding aabb
        test = new Aabb2d();
        test.minX = 0.5;
        test.minY = 0.5;
        test.maxX = 2;
        test.maxY = 2;

        Assert.assertTrue(aabb.isColliding(test));
        Assert.assertFalse(aabb.isInside(test));

        // Test inside aabb
        test = new Aabb2d();
        test.minX = 0.5;
        test.minY = 0.5;
        test.maxX = 0.75;
        test.maxY = 0.75;

        Assert.assertTrue(aabb.isColliding(test));
        Assert.assertTrue(aabb.isInside(test));
    }
}
