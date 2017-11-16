package xyz.upperlevel.openverse.server.command;

import java.util.List;

public interface CommandInterpreter {
    boolean process(CommandSender sender, CommandRegistry registry, String line);

    List<String> tabComplete(CommandSender sender, CommandRegistry registry, String line);
}
