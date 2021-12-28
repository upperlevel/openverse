package xyz.upperlevel.openverse.server.command.def;

import lombok.Getter;
import xyz.upperlevel.openverse.inventory.Slot;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.command.Command;
import xyz.upperlevel.openverse.server.command.CommandSender;
import xyz.upperlevel.openverse.server.command.CommandUtil;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;

import java.util.*;
import java.util.stream.Collectors;

import static xyz.upperlevel.openverse.console.ChatColor.GREEN;
import static xyz.upperlevel.openverse.console.ChatColor.RED;

public class GiveCommand implements Command {
    @Getter
    private final OpenverseServer server;

    public GiveCommand(OpenverseServer server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Gives items to the player";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("get", "invadd");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        PlayerInventory inv = server.getPlayerManager().getPlayers().iterator().next().getInventory();
        if (args.size() < 2) {
            sender.sendMessage(RED + "Usage: \\give <player> <item> [count] [variant=value...]");
        } else {
            Player player = server.getPlayerManager().getPlayer(args.get(0));
            if (player == null) {
                sender.sendMessage(RED + "Cannot find player '" + args.get(0) + "'");
                return;
            }
            int count;
            if (args.size() >= 3) {
                try {
                    count = Integer.parseInt(args.get(2));
                } catch (NumberFormatException e) {
                    sender.sendMessage(RED + "Invalid number in count parameter");
                    return;
                }
            } else {
                count = 1;
            }

            List<String> variants;
            if (args.size() >= 4) {
                variants = args.subList(3, args.size());
            } else {
                variants = Collections.emptyList();
            }

            ItemStack item = parseItem(args.get(1), count, variants);
            if (item == null) {
                sender.sendMessage("Cannot find item type '" + args.get(1) + "'");
            }
            if (!giveItem(item, player)) {
                sender.sendMessage(RED + "Player has no space in inventory");
                return;
            }
            sender.sendMessage(GREEN + "Item " + item + " Given!");
        }
    }

    private boolean giveItem(ItemStack item, Player player) {
        PlayerInventory inventory = player.getInventory();
        for (Slot slot : inventory) {
            if (slot.getContent().isEmpty()) {
                slot.swap(item);
                return true;
            }
        }
        return false;
    }

    private ItemStack parseItem(String itemType, int count, List<String> data) {
        ItemType item = server.getResources().itemTypes().entry(itemType);
        if (item == null) return null;
        if (data.isEmpty()) {
            return item.getStackWithData(count, Collections.emptyMap());
        }
        return item.getStackWithData(
                count,
                data.stream()
                        .map(s -> {
                            String[] strs = s.split("=");
                            return strs.length != 2 ? null : new AbstractMap.SimpleImmutableEntry<>(strs[0], strs[1]);
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }

    @Override
    public List<String> tabComplete(CommandSender sender, List<String> args) {
        if (args.isEmpty()) {
            return server.getPlayerManager().getPlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        if (args.size() == 1) {
            String partialArg = args.get(0);
            return CommandUtil.searchCompletitions(server.getPlayerManager().getPlayers().stream().map(Player::getName), partialArg);
        }
        if (args.size() == 2) {
            return CommandUtil.itemTypeComplete(server.getResources().itemTypes(), args.get(0));
        }
        return Collections.emptyList();
    }
}
