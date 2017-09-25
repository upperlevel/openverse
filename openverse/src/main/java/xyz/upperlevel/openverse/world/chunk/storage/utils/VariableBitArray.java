package xyz.upperlevel.openverse.world.chunk.storage.utils;


import xyz.upperlevel.openverse.util.MathUtil;

/**
 * An array with entries of variable length
 */
public class VariableBitArray {
    private static final int ENTRY_LEN = Long.BYTES * 8;
    private final long[] array;
    private final long maxValueMask;
    private final int bitsPerEntry;
    private final int capacity;


    public VariableBitArray(int entryBits, int capacity) {
        this.capacity = capacity;
        this.bitsPerEntry = entryBits;
        //Math.ceil(bitsPerEntry * capacity / 8)
        this.array = new long[MathUtil.roundUp(bitsPerEntry * capacity, ENTRY_LEN) / ENTRY_LEN];
        //This mask should have all bits usable by an entry set to one
        //This is obtainable by getting the biggest number obtainable
        this.maxValueMask = (1L << bitsPerEntry) - 1;
    }

    public void set(int index, int value) {
        checkIndex(index);
        int bitIndex = index * bitsPerEntry;
        int realIndex = bitIndex / ENTRY_LEN;
        int bitOffest = bitIndex % ENTRY_LEN;

        array[realIndex] = array[realIndex] & ~(maxValueMask << bitOffest) | (value & maxValueMask) << bitOffest;

        int written = ENTRY_LEN - bitOffest;
        if (written < bitsPerEntry) {
            int i = realIndex + 1;
            array[i] = array[i] & ~(maxValueMask >>> written) | (value & maxValueMask) >>> written;
        }
    }

    public int get(int index) {
        checkIndex(index);
        int bitIndex = index * bitsPerEntry;
        int realIndex = bitIndex / ENTRY_LEN;
        int bitOffest = bitIndex % ENTRY_LEN;

        int written = ENTRY_LEN - bitOffest;
        if (written >= bitsPerEntry) {
            return (int) ((array[realIndex] >>> bitOffest) & maxValueMask);
        } else {
            return (int) (((array[realIndex] >>> bitOffest) | (array[realIndex + 1] << written)) & maxValueMask);
        }
    }

    protected void checkIndex(int i) {
        if (i > capacity) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Capacity: " + capacity);
        }
    }

    public int capacity() {
        return capacity;
    }

    public int bitsPerEntry() {
        return bitsPerEntry;
    }

    public long getMaxValue() {
        return maxValueMask;
    }

    public long[] getBackingArray() {
        return array;
    }
}
