package xyz.upperlevel.openverse;

import org.junit.Test;
import xyz.upperlevel.openverse.world.chunk.storage.utils.VariableBitArray;

import static org.junit.Assert.assertEquals;

public class VariableBitArrayTest {

    @Test
    public void simpleTest() {
        VariableBitArray array = new VariableBitArray(2, 4);
        array.set(0, 3);
        array.set(1, 2);
        array.set(2, 1);
        array.set(3, 0);
        assertEquals(3, array.get(0));
        assertEquals(2, array.get(1));
        assertEquals(1, array.get(2));
        assertEquals(0, array.get(3));
        assertEquals(0b00_01_10_11, array.getBackingArray()[0]);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outOufBoundsText() {
        VariableBitArray array = new VariableBitArray(2, 4);
        array.set(5, 4);
    }

    @Test
    public void otherTest() {
        VariableBitArray array = new VariableBitArray(Integer.BYTES * 8, 8);
        long c = ((long)Integer.MAX_VALUE - Integer.MIN_VALUE)/8;
        for (int i = 0; i < 8; i++) {
            array.set(i, (int) (c * i));
        }
        for (int i = 0; i < 8; i++) {
            assertEquals((int)(c * i), array.get(i));
        }
    }

    @Test
    public void oddLengthTest() {
        VariableBitArray array = new VariableBitArray(25, 5);
        int[] tests = {41847297, 1549070137, 123014888, 235795758, 5972058}; //Some random typed numbers
        for (int i = 0; i < tests.length; i++) {
            tests[i] &= array.getMaxValue();//Limit them at the max value
            array.set(i, tests[i]);
        }
        for (int i = 0; i < tests.length; i++) {
            assertEquals(tests[i], array.get(i));
        }
    }
}
