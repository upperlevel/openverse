package xyz.upperlevel.openverse.server.command.def;

import lombok.Getter;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.command.Command;
import xyz.upperlevel.openverse.server.command.CommandSender;
import xyz.upperlevel.openverse.server.command.CommandUtil;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static xyz.upperlevel.openverse.console.ChatColor.*;

/**
 * TESTING COMMAND
 * <br>Used to change or display the player's hotbar
 * <br>Usage: "hotbar [(hand [newSlot])|(set slotId itemId [count])]
 */
public class HotbarCommand implements Command {
    @Getter
    private final OpenverseServer server;

    public HotbarCommand(OpenverseServer server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return "hotbar";
    }

    @Override
    public String getDescription() {
        return "[TEST]Gives permission to edit a player's hotbar";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("quickbar", "bar");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        PlayerInventory inv = server.getPlayerManager().getPlayers().iterator().next().getInventory();
        if (args.isEmpty()) {
            printInventory(sender, inv);
        } else {
            switch (args.get(0).toLowerCase()) {
                case "hand":
                    if (args.size() < 2) {
                        printHand(sender, inv);
                    } else {
                        setHand(sender, inv, args.get(1));
                    }
                    break;
                case "set":
                    if (args.size() < 3) {
                        sender.sendMessage(RED + "Usage: quickbar set <slotId> <itemId> [count]");
                    } else {
                        setSlot(sender, inv, args.get(1), args.get(2), args.size() > 3 ? args.get(3) : null);
                    }
            }
        }
    }

    private void printInventory(CommandSender sender, PlayerInventory inventory) {
        sender.sendMessage(BLUE + "Printing player's hotbar");
        for (int i = 0; i < 9; i++) {
            ItemStack stack = inventory.getHotbarItem(i);
            sender.sendMessage(BLUE + "" + i + ") " + GREEN + (stack.isEmpty() ? "" : stack));
        }
    }

    private void printHand(CommandSender sender, PlayerInventory inventory) {
        sender.sendMessage(
                BLUE + "Current player hand: " + GREEN + inventory.getHandSlot()
                + BLUE + " -> "
                + GREEN + inventory.getHandItem()
        );
    }

    private void setHand(CommandSender sender, PlayerInventory inventory, String newHand) {
        //This method could receive the new hand index or the new hand item
        int handSlot;
        try {
            handSlot = Integer.parseInt(newHand);
        } catch (NumberFormatException e) {
            handSlot = -1;
        }
        if (handSlot < 0 || handSlot > 8) {
            ItemType item = server.getResources().itemTypes().entry(newHand);
            if (item == null) {
                sender.sendMessage(RED + "Cannot find item '" + GREEN + newHand + BLUE + "'");
                return;
            }
            inventory.setHandItem(new ItemStack(item));
        } else {
            inventory.setHandSlot(handSlot);
        }
        printHand(sender, inventory);
    }

    private void setSlot(CommandSender sender, PlayerInventory inventory, String slotIdStr, String itemIdStr, String countStr) {
        int slotId;
        try {
            slotId = Integer.parseInt(slotIdStr);
        } catch (NumberFormatException e) {
            slotId = -1;
        }

        if (slotId < 0 || slotId > 8) {
            sender.sendMessage(RED + "Invalid slot id: '" + slotIdStr + "'");
            return;
        }

        ItemType itemType = server.getResources().itemTypes().entry(itemIdStr);
        if (itemType == null) {
            sender.sendMessage(RED + "Cannot find item '" + itemIdStr + "'");
            return;
        }

        int count;

        if (countStr == null) {
            count = 1;
        } else {
            try {
                count = Integer.parseInt(countStr);
            } catch (NumberFormatException mis) {
                count = -1;
            }
            if (count < 0 || count > itemType.getMaxStack()) {
                sender.sendMessage(RED + "Invalid item count: '" + countStr + "'");
                return;
            }
        }
        ItemStack itemStack = new ItemStack(itemType, count);
        inventory.setHotbarItem(slotId, itemStack);
        sender.sendMessage(BLUE + "Item at slot " + GREEN + slotId + BLUE + ": " + GREEN + itemStack);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, List<String> args) {
        if (args.isEmpty()) {
            return Arrays.asList("hand", "set");
        }
        if (args.size() == 1) {
            String partialArg = args.get(0).toLowerCase();
            if (partialArg.equals("hand") || partialArg.equals("set")) {
                return Collections.emptyList();
            }
            return CommandUtil.searchCompletitions(Stream.of("hand", "set"), partialArg);
        }
        if (args.size() == 3 && args.get(0).equalsIgnoreCase("set")) {
            return CommandUtil.itemTypeComplete(server.getResources().itemTypes(), args.get(2));
        }
        return Collections.emptyList();
    }
}
