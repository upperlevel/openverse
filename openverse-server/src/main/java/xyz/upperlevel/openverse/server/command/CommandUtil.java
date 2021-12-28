package xyz.upperlevel.openverse.server.command;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.item.ItemTypeRegistry;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CommandUtil {

    public static List<String> searchCompletitions(Stream<String> in, Predicate<String> filter) {
        return in.filter(filter).collect(Collectors.toList());
    }

    public static List<String> searchCompletitions(Stream<String> in, String prefix) {
        return searchCompletitions(in, s -> startsWithIgnoreCase(s, prefix));
    }

    public static List<String> itemTypeComplete(ItemTypeRegistry itemTypeRegistry, String input) {
        return searchCompletitions(
                itemTypeRegistry.entries().stream().map(ItemType::getId),
                input
        );
    }

    public static List<String> blockTypeComplete(BlockTypeRegistry blockTypeRegistry, String input) {
        return searchCompletitions(
                blockTypeRegistry.entries().stream().map(BlockType::getId),
                input
        );
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    private CommandUtil(){}
}
