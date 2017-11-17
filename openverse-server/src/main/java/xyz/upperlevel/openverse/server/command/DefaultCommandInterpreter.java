package xyz.upperlevel.openverse.server.command;

import xyz.upperlevel.openverse.resource.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public int tabComplete(CommandSender sender, CommandRegistry registry, String line, List<String> completions) {
        List<String> tokens = tokenize(line);
        if (tokens.isEmpty()) {
            return line.length();
        }
        Command command = registry.get(tokens.get(0));
        if (command == null) {
            return tabCompleteCmdName(registry, line, tokens, completions);
        } else {
            return tabCompleteCmdArgs(sender, command, line, tokens, completions);
        }
    }

    private int tabCompleteCmdArgs(CommandSender sender, Command command, String line, List<String> tokens, List<String> completions) {
        List<String> res = command.tabComplete(sender, tokens.subList(1, tokens.size()));
        if (res == null) {
            res = Collections.emptyList();
        }

        if (tokens.size() <= 1) {//Only command without arguments
            completions.addAll(res);
            return line.length();
        }

        int postfixSpaces = countPostfixSpaces(line);
        int lastTokenLen = tokens.get(tokens.size() - 1).length();
        char quotes = checkQuotes(line.charAt(line.length() - postfixSpaces - lastTokenLen - 1));

        if (quotes != 0) {
            res.stream().map(s -> quotes + s + quotes).collect(Collectors.toCollection(() -> completions));
        } else completions.addAll(res);

        return line.length() - lastTokenLen - (quotes != 0 ? 1 : 0) - postfixSpaces;
    }

    protected Stream<String> commandNameAndAliases(CommandRegistry registry) {
        return registry.getCommands()
                .stream()
                .flatMap(c -> Stream.concat(Stream.of(c.getName()), c.getAliases().stream()));
    }

    protected int tabCompleteCmdName(CommandRegistry registry, String line, List<String> tokens, List<String> completitons) {
        int prefixSpaces = countPrefixSpaces(line);
        char startQuotes = checkQuotes(line.charAt(prefixSpaces));
        String partialName = tokens.get(0);
        Stream<String> filtered = commandNameAndAliases(registry).filter(s -> CommandUtil.startsWithIgnoreCase(s, partialName));
        if (startQuotes != 0) {
            filtered = filtered.map(s -> startQuotes + s + startQuotes);
        }
        filtered.collect(Collectors.toCollection(() -> completitons));
        return prefixSpaces;
    }

    private int countPrefixSpaces(String line) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            if (line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                return i;
            }
        }
        return len;
    }

    private int countPostfixSpaces(String line) {
        int len = line.length();
        for (int i = len - 1; i >= 0; i--) {
            if (line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                return len - i - 1;
            }
        }
        return len;
    }

    private char checkQuotes(char quote) {
        return quote == '"' || quote == '\'' ? quote : 0;
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
