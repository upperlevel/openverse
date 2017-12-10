package xyz.upperlevel.openverse.util.config.formats;

import com.google.common.collect.ImmutableMap;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static xyz.upperlevel.openverse.network.SerialUtil.readString;
import static xyz.upperlevel.openverse.network.SerialUtil.writeString;
import static xyz.upperlevel.openverse.util.config.formats.VarDataUtil.readUnsignedVarInt;
import static xyz.upperlevel.openverse.util.config.formats.VarDataUtil.writeUnsignedVarInt;

public class ObfDataFormat implements DataFormat {
    public static final ObfDataFormat INSTANCE = new ObfDataFormat();

    public static final int INT_8           = 0;
    public static final int INT_16          = 1;
    public static final int INT_32          = 2;
    public static final int INT_64          = 3;
    public static final int FLOAT_32        = 4;
    public static final int FLOAT_64        = 5;
    public static final int STRING          = 6;
    public static final int SECTION         = 7;
    public static final int ARRAY_INT_8     = 8;
    public static final int ARRAY_INT_16    = 9;
    public static final int ARRAY_INT_32    = 10;
    public static final int ARRAY_INT_64    = 11;
    public static final int ARRAY_FLOAT_32  = 12;
    public static final int ARRAY_FLOAT_64  = 13;
    public static final int ARRAY_STRING    = 14;
    public static final int ARRAY_SECTION   = 15;

    @Override
    public Map<String, Object> deserialize(DataInput in) throws IOException {
        return readSection(in);
    }


    public Map<String, Object> readSection(DataInput in) throws IOException {
        final int entries = readUnsignedVarInt(in);
        ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();
        for (int i = 0; i < entries; i++) {
            String key = readString(in);
            Object value = readValue(in);
            map.put(key, value);
        }
        return map.build();
    }

    private Object readValue(DataInput in) throws IOException {
        int typeId = in.readUnsignedByte();
        switch (typeId) {
            case INT_8:
                return in.readByte();
            case INT_16:
                return in.readShort();
            case INT_32:
                return in.readInt();
            case INT_64:
                return in.readLong();
            case FLOAT_32:
                return in.readFloat();
            case FLOAT_64:
                return in.readDouble();
            case STRING:
                return readString(in);
            case SECTION:
                return readSection(in);
            case ARRAY_INT_8:
                return readByteArray(in);
            case ARRAY_INT_16:
                return readShortArray(in);
            case ARRAY_INT_32:
                return readIntArray(in);
            case ARRAY_INT_64:
                return readLongArray(in);
            case ARRAY_FLOAT_32:
                return readFloatArray(in);
            case ARRAY_FLOAT_64:
                return readDoubleArray(in);
            case ARRAY_STRING:
                return readStringArray(in);
            case ARRAY_SECTION:
                return readSectionArray(in);
            default:
                throw new IllegalStateException("Unspecified OBF type: " + typeId);
        }
    }

    public List<Byte> readByteArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        Byte[] out = new Byte[length];
        for (int i = 0; i < length; i++) {
            out[i] = in.readByte();
        }
        return Arrays.asList(out);
    }

    public List<Short> readShortArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        Short[] out = new Short[length];
        for (int i = 0; i < length; i++) {
            out[i] = in.readShort();
        }
        return Arrays.asList(out);
    }

    public List<Integer> readIntArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        Integer[] out = new Integer[length];
        for (int i = 0; i < length; i++) {
            out[i] = in.readInt();
        }
        return Arrays.asList(out);
    }

    public List<Long> readLongArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        Long[] out = new Long[length];
        for (int i = 0; i < length; i++) {
            out[i] = in.readLong();
        }
        return Arrays.asList(out);
    }

    public List<Float> readFloatArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        Float[] out = new Float[length];
        for (int i = 0; i < length; i++) {
            out[i] = in.readFloat();
        }
        return Arrays.asList(out);
    }

    public List<Double> readDoubleArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        Double[] out = new Double[length];
        for (int i = 0; i < length; i++) {
            out[i] = in.readDouble();
        }
        return Arrays.asList(out);
    }

    public List<String> readStringArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        String[] out = new String[length];
        for (int i = 0; i < length; i++) {
            out[i] = readString(in);
        }
        return Arrays.asList(out);
    }

    @SuppressWarnings("unchecked")// Really hating java
    public List<Map<String, Object>> readSectionArray(DataInput in) throws IOException {
        int length = readUnsignedVarInt(in);
        Map<String, Object>[] out = new Map[length];
        for (int i = 0; i < length; i++) {
            out[i] = readSection(in);
        }
        return Arrays.asList(out);
    }

    @Override
    public void serialize(Map<String, Object> map, DataOutput out) throws IOException {
        writeSection(map, out);
    }

    @SuppressWarnings("unchecked")
    public void writeValue(Object value, DataOutput out) throws IOException {
        Class<?> clazz = value.getClass();
        if (clazz == Byte.class) {
            out.write(INT_8);
            out.write((Byte) value);
        } else if (clazz == Short.class) {
            out.write(INT_16);
            out.writeShort((Short) value);
        } else if (clazz == Integer.class) {
            out.write(INT_32);
            out.writeInt((Integer) value);
        } else if (clazz == Long.class) {
            out.write(INT_64);
            out.writeLong((Long) value);
        } else if (clazz == Float.class) {
            out.write(FLOAT_32);
            out.writeFloat((Float) value);
        } else if (clazz == Double.class) {
            out.write(FLOAT_64);
            out.writeDouble((Double) value);
        } else if (clazz == String.class) {
            out.write(STRING);
            writeString((String) value, out);
        } else if (value instanceof Map) {
            out.write(SECTION);
            writeSection((Map) value, out);
        } else if (value instanceof List) {
            List<?> l = (List<?>) value;
            if (l.isEmpty()) {
                writeByteArray(new byte[]{}, out);
                return;
            }
            Class<?> lclazz = l.get(0).getClass();
            if (lclazz == Byte.class) {
                out.write(ARRAY_INT_8);
                writeByteArray((List<Byte>) l, out);
            } else if (lclazz == Short.class) {
                out.write(ARRAY_INT_16);
                writeShortArray((List<Short>) l, out);
            } else if (lclazz == Integer.class) {
                out.write(ARRAY_INT_32);
                writeIntArray((List<Integer>) l, out);
            } else if (lclazz == Long.class) {
                out.write(ARRAY_INT_64);
                writeLongArray((List<Long>) l, out);
            } else if (lclazz == Float.class) {
                out.write(ARRAY_FLOAT_32);
                writeFloatArray((List<Float>) l, out);
            } else if (lclazz == Double.class) {
                out.write(ARRAY_FLOAT_64);
                writeDoubleArray((List<Double>) l, out);
            } else if (lclazz == String.class) {
                out.write(ARRAY_STRING);
                writeStringArray((List<String>) l, out);
            } else if (lclazz == Map.class) {
                out.writeInt(ARRAY_SECTION);
                writeSectionArray((List<Map>) l, out);
            } else if (value instanceof Enum<?>) {
                writeEnumArray((List<Enum<?>>) value, out);
            } else throw new IllegalStateException("Cannot serialize class " + lclazz);
        } else if (clazz == byte[].class) {
            out.write(ARRAY_INT_8);
            writeByteArray((byte[])value, out);
        } else if (clazz == short[].class) {
            out.write(ARRAY_INT_16);
            writeShortArray((short[])value, out);
        } else if (clazz == int[].class) {
            out.write(ARRAY_INT_32);
            writeIntArray((int[])value, out);
        } else if (clazz == long[].class) {
            out.write(ARRAY_INT_64);
            writeLongArray((long[]) value, out);
        } else if (clazz == float[].class) {
            out.write(ARRAY_FLOAT_32);
            writeFloatArray((float[]) value, out);
        } else if (clazz == double[].class) {
            out.write(ARRAY_FLOAT_64);
            writeDoubleArray((double[]) value, out);
        } else if (clazz == String[].class) {
            out.write(ARRAY_STRING);
            writeStringArray(Arrays.asList((String[]) value), out);
        } else if (clazz == Map[].class) {
            out.write(ARRAY_SECTION);
            writeSectionArray(Arrays.asList((Map[]) value), out);
        } else if (clazz == Boolean.class) {
            out.write(INT_8);
            out.write((Boolean) value ? 1 : 0);
        } else if (value instanceof Enum) {
            writeUnsignedIntChooseSize(((Enum) value).ordinal(), out);
        } else if (value instanceof Enum[]) {
            writeEnumArray(Arrays.asList((Enum[]) value), out);
        } else if (value instanceof DataSerializable) {
            // Hoping that this doesn't create a circular dependency, but this is up to the user
            writeValue(((DataSerializable) value).serialize(), out);
        } else throw new IllegalStateException("Cannot serialize class " + clazz);
    }

    public void writeSection(Map<String, Object> map, DataOutput out) throws IOException {
        writeUnsignedVarInt(map.size(), out);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            writeString(entry.getKey(), out);
            writeValue(entry.getValue(), out);
        }
    }

    /**
     * Writes an integer value searching for the minimum occupying size.
     * <br>If the int can be stored in a byte, store it as a byte, same as short,
     * <br>If none of the above could store the number, use an int as the fallback.
     * @param i the number to store
     * @param out the output to write to
     * @throws IOException See {@link DataOutput#write(int)}, exception
     */
    public void writeIntChooseSize(int i, DataOutput out) throws IOException {
        if (((byte) i) == i) {
            out.write(INT_8);
            out.write(i);
        } else if (((short) i) == i) {
            out.write(INT_16);
            out.writeShort(i);
        } else {
            out.write(INT_32);
            out.writeInt(i);
        }
    }

    /**
     * Writes an integer value searching for the minimum occupying size.
     * <br>If the int can be stored in an unsigned byte (0 to 255), store it as a byte, same as short (0 to 65535),
     * <br>If none of the above could store the number, use an int as the fallback.
     * @param i the number to store
     * @param out the output to write to
     * @throws IOException See {@link DataOutput#write(int)}, exception
     */
    public void writeUnsignedIntChooseSize(int i, DataOutput out) throws IOException {
        if (i <= 0xFF) {
            out.write(INT_8);
            out.write(i);
        } else if (i <= 0xFFFF) {
            out.write(INT_16);
            out.writeShort(i);
        } else {
            out.write(INT_32);
            out.writeInt(i);
        }
    }

    public void writeByteArray(byte[] value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.length, out);
        for (byte v : value) {
            out.write(v);
        }
    }

    public void writeByteArray(List<Byte> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (byte v : value) {
            out.write(v);
        }
    }

    public void writeShortArray(short[] value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.length, out);
        for (short v : value) {
            out.writeShort(v);
        }
    }

    public void writeShortArray(List<Short> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (short v : value) {
            out.writeShort(v);
        }
    }

    public void writeIntArray(int[] value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.length, out);
        for (int v : value) {
            out.writeInt(v);
        }
    }

    public void writeIntArray(List<Integer> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (int v : value) {
            out.writeInt(v);
        }
    }

    public void writeLongArray(long[] value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.length, out);
        for (long v : value) {
            out.writeLong(v);
        }
    }

    public void writeLongArray(List<Long> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (long v : value) {
            out.writeLong(v);
        }
    }

    public void writeFloatArray(float[] value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.length, out);
        for (float v : value) {
            out.writeFloat(v);
        }
    }

    public void writeFloatArray(List<Float> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (float v : value) {
            out.writeFloat(v);
        }
    }

    public void writeDoubleArray(double[] value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.length, out);
        for (double v : value) {
            out.writeDouble(v);
        }
    }

    public void writeDoubleArray(List<Double> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (double v : value) {
            out.writeDouble(v);
        }
    }

    public void writeStringArray(List<String> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (String v : value) {
            writeString(v, out);
        }
    }

    @SuppressWarnings("unchecked")
    public void writeSectionArray(List<Map> value, DataOutput out) throws IOException {
        writeUnsignedVarInt(value.size(), out);
        for (Map<String, Object> v : value) {
            writeSection(v, out);
        }
    }

    public void writeEnumArray(List<Enum<?>> value, DataOutput out) throws IOException {
        if (value.size() == 0) {
            writeByteArray(new byte[]{}, out);
            return;
        }
        // Find minimum size (should be Byte most of the times)
        int size = INT_8;
        for (Enum e : value) {
            int o = e.ordinal();
            if (o <= 0xFF) continue;
            if (o <= 0xFFFF) {
                if (size < 1) {
                    size = INT_16;
                }
            } else {
                size = INT_32;
            }
        }
        out.write(size);

        if (size == INT_8) {
            for (Enum e : value) {
                out.write(e.ordinal());
            }
        } else if (size == INT_16) {
            for (Enum e : value) {
                out.writeShort(e.ordinal());
            }
        } else {
            for (Enum e : value) {
                out.writeInt(e.ordinal());
            }
        }
    }
}
