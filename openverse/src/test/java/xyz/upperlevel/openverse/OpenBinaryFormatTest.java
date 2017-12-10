package xyz.upperlevel.openverse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Bytes;
import org.junit.Test;
import xyz.upperlevel.openverse.util.config.formats.ObfDataFormat;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class OpenBinaryFormatTest {

    public static byte[] serializeObf(Map<String, Object> map) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        DataOutput out = new DataOutputStream(arrayOutputStream);
        ObfDataFormat.INSTANCE.serialize(map, out);
        return arrayOutputStream.toByteArray();
    }

    public static Map<String, Object> deserializeObf(byte[] data) throws IOException {
        return ObfDataFormat.INSTANCE.deserialize(new DataInputStream(new ByteArrayInputStream(data)));
    }

    public static void deepEquals(Map<?, ?> a, Map<?, ?> b) {
        assertEquals("Different map sizes", a.size(), b.size());
        for (Map.Entry<?, ?> entry : a.entrySet()) {
            Object bVal = b.get(entry.getKey());
            assertEquals("Different classes", a.getClass(), b.getClass());
            if(bVal.getClass().isArray()) {
                assertArrayEquals((Object[])entry.getValue(), (Object[])bVal);
            } else if (bVal instanceof Map) {
                deepEquals((Map)entry.getValue(), (Map)bVal);
            } else {
                assertEquals(entry.getValue(), bVal);
            }
        }
    }

    public static void testWith(Map<String, Object> deserialized, byte[] serialized) throws IOException {
        //System.out.println("D: {" + DatatypeConverter.printHexBinary(serializeObf(deserialized)) + "}");
        //System.out.println("S: {" + DatatypeConverter.printHexBinary(serialized) + "}");
        assertArrayEquals(
                serialized,
                serializeObf(deserialized)
        );
        deepEquals(
                deserialized,
                deserializeObf(serialized)
        );
    }

    @Test
    public void exampleTest() throws IOException {
        // Test found at https://github.com/upperlevel/openverse/wiki/Open-Binary-Format
        testWith(
                ImmutableMap.of(
                        "id", 314,
                        "name", "paolo",
                        "age", (byte) 17,
                        "items", new Map[]{
                                ImmutableMap.of(
                                        "type", "diamond",
                                        "amount", (byte) 32
                                ),
                                ImmutableMap.of(
                                        "type", "pickaxe"
                                ),
                                ImmutableMap.of(
                                        "type", "dirt",
                                        "amount", (byte) 53
                                ),
                        }
                ),
                new byte[] {
                        0x04,
                        0x69, 0x64, 0x00, 0x02, 0x00, 0x00, 0x01, 0x3A,
                        0x6E, 0x61, 0x6D, 0x65, 0x00, 0x06, 0x70, 0x61, 0x6F, 0x6C, 0x6F, 0x00,
                        0x61, 0x67, 0x65, 0x00, 0x00, 0x11,
                        0x69, 0x74, 0x65, 0x6D, 0x73, 0x00, 0x0F, 0x03,
                        0x02,
                        0x74, 0x79, 0x70, 0x65, 0x00, 0x06, 0x64, 0x69, 0x61, 0x6D, 0x6F, 0x6E, 0x64, 0x00,
                        0x61, 0x6D, 0x6F, 0x75, 0x6E, 0x74, 0x00, 0x00, 0x20,
                        0x01,
                        0x74, 0x79, 0x70, 0x65, 0x00, 0x06, 0x70, 0x69, 0x63, 0x6B, 0x61, 0x78, 0x65, 0x00,
                        0x02,
                        0x74, 0x79, 0x70, 0x65, 0x00, 0x06, 0x64, 0x69, 0x72, 0x74, 0x00,
                        0x61, 0x6D, 0x6F, 0x75, 0x6E, 0x74, 0x00, 0x00, 0x35,
                }
        );
    }

    @Test
    public void sectionNestingTest() throws IOException {
        // Note: the protocol doesn't support boolean types but emulates them with c-style booleans,
        // We put 1 in this example because if we put "true" it would fail saying that true is different from 1
        // This difference isn't really noticeable if working behind the Config API
        testWith(
                ImmutableMap.of(
                        "map", ImmutableMap.of(
                                "map", ImmutableMap.of(
                                        "map", ImmutableMap.of(
                                                "working", (byte) 1
                                        )
                                )
                        )
                ),
                new byte[] {
                        0x01,
                        0x6D, 0x61, 0x70, 0x00, 0x07,
                        0x01,
                        0x6D, 0x61, 0x70, 0x00, 0x07,
                        0x01,
                        0x6D, 0x61, 0x70, 0x00, 0x07,
                        0x01,
                        0x77, 0x6F, 0x72, 0x6B, 0x69, 0x6E, 0x67, 0x00, 0x00, 0x01
                }
        );
    }

    @Test
    public void bigArrayTest() throws IOException {
        testWith(
                ImmutableMap.of(
                        "array", IntStream.range(0, 300).boxed().collect(Collectors.toList())
                ),
                Bytes.concat(new byte[]{
                                0x01,
                                0x61, 0x72, 0x72, 0x61, 0x79, 0x00, 0x0A,
                                (byte) 0xAC, 0x02,
                        },
                        Bytes.toArray(IntStream.range(0, 300).mapToObj(i -> Bytes.asList(ByteBuffer.allocate(4).putInt(i).array()))
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList()))
                )
        );
    }

    @Test
    public void listArrayEqualityTest() throws IOException {
        // Yep, that's not a bug nor a feature, the protocol doesn't distinguish between array and lists
        assertArrayEquals(
                serializeObf(
                        ImmutableMap.of(
                                "array", new int[] {
                                        123, 456, 789, 150
                                }
                        )
                ),
                serializeObf(
                        ImmutableMap.of(
                                "array", ImmutableList.of(
                                        123, 456, 789, 150
                                )
                        )
                )
        );
    }

    @Test
    public void arrayStringTest() throws IOException {
        testWith(
                ImmutableMap.of(
                        "names1", ImmutableList.of(
                                "ted", "english", "idk", "mariangiongiangela", ""
                        ),
                        "names2", ImmutableList.of(
                                "john", "steins", "gate", "christina"
                        )
                ),
                new byte[] {
                        0x02,
                        0x6E, 0x61, 0x6D, 0x65, 0x73, 0x31, 0x00, 0x0E,
                        0x05,
                        0x74, 0x65, 0x64, 0x00,
                        0x65, 0x6E, 0x67, 0x6C, 0x69, 0x73, 0x68, 0x00,
                        0x69, 0x64, 0x6B, 0x00,
                        0x6D, 0x61, 0x72, 0x69, 0x61, 0x6E, 0x67, 0x69, 0x6F, 0x6E, 0x67, 0x69, 0x61, 0x6E, 0x67, 0x65, 0x6C, 0x61, 0x00,
                        0x00,
                        0x6E, 0x61, 0x6D, 0x65, 0x73, 0x32, 0x00, 0x0E,
                        0x04,
                        0x6A, 0x6F, 0x68, 0x6E, 0x00,
                        0x73, 0x74, 0x65, 0x69, 0x6E, 0x73, 0x00,
                        0x67, 0x61, 0x74, 0x65, 0x00,
                        0x63, 0x68, 0x72, 0x69, 0x73, 0x74, 0x69, 0x6E, 0x61, 0x00
                }
        );
    }

    @Test
    public void unicodeUnsupportedTest() throws IOException {
        // Why should I make a test about something that isn't supported?
        Map<String, Object> s = ImmutableMap.of("test", "la pizza è buona");
        assertNotEquals(s, deserializeObf(serializeObf(s)));
    }
}
