package xyz.upperlevel.openverse.server.command.def;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.server.command.Command;
import xyz.upperlevel.openverse.server.command.CommandRegistry;
import xyz.upperlevel.openverse.server.command.CommandSender;
import xyz.upperlevel.openverse.server.command.CommandUtil;

import java.util.*;
import java.util.stream.Collectors;

import static xyz.upperlevel.openverse.console.ChatColor.*;

@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final CommandRegistry registry;

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Get the command list or a specific command's description";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("man");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.isEmpty()) {
            printPage(sender);
        } else {
            printCommandDescription(sender, args.get(0));
        }
    }

    public void printCommandDescription(CommandSender sender, String commandName) {
        Command command = registry.get(commandName);
        if (command == null) {
            sender.sendMessage(RED + "Command '" + commandName + "' not found!");
            return;
        }
        sender.sendMessage(BLUE + "Info on command " + command.getName());
        sender.sendMessage(BLUE + "Name: '" + GREEN + command.getName() + BLUE + "'");
        sender.sendMessage(BLUE + "Aliases: '" + GREEN + command.getAliases() + BLUE + "'");
        sender.sendMessage(BLUE + "Description: '" + GREEN + command.getDescription() + BLUE + "'");
    }

    public void printPage(CommandSender sender) {
        Set<Command> commands = registry.getCommands();
        sender.sendMessage(BLUE + "Printing " + BLUE + commands.size() + BLUE + " commands:");
        for (Command command : registry) {
            sender.sendMessage(BLUE + "- " + GREEN + command.getName());
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, List<String> args) {
        if (args.isEmpty()) {
            return registry.getCommands().stream().map(Command::getName).collect(Collectors.toList());
        } else if (args.size() == 1) {
            return CommandUtil.searchCompletitions(registry.getCommands().stream().map(Command::getName), args.get(0));
        } else {
            return Collections.emptyList();
        }
    }
}
