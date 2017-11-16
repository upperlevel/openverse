package xyz.upperlevel.openverse.server.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static xyz.upperlevel.openverse.console.ChatColor.CYAN;
import static xyz.upperlevel.openverse.console.ChatColor.RED;

public class DefaultCommandInterpreter implements CommandInterpreter {
    @Override
    public boolean process(CommandSender sender, CommandRegistry registry, String line) {
        List<String> tokens = tokenize(line);
        if (tokens.isEmpty()) {
            return false;
        }
        Command command = registry.get(tokens.get(0));
        if (command == null) {
            sender.sendMessage(RED + "Command " + CYAN + tokens.get(0) + RED + " not found");
            return false;
        }
        command.execute(sender, tokens.subList(1, tokens.size()));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, CommandRegistry registry, String line) {
        List<String> tokens = tokenize(line);
        if (tokens.isEmpty()) {
            return emptyList();
        }
        Command command = registry.get(tokens.get(0));
        if (command == null) {
            return emptyList();
        }
        List<String> res = command.tabComplete(sender, tokens.subList(1, tokens.size()));
        return res != null ? res : emptyList();
    }

    public List<String> tokenize(String line) {//TODO add escapes and attached-quotes support (es: echo "Hello"World shouldn't have spaces)
        List<String> matchList = new ArrayList<>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(line);
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                // Add double-quoted string without the quotes
                matchList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null) {
                // Add single-quoted string without the quotes
                matchList.add(regexMatcher.group(2));
            } else {
                // Add unquoted word
                matchList.add(regexMatcher.group());
            }
        }
        return matchList;
    }
}
