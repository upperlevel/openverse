package xyz.upperlevel.openverse.server.command;

import java.util.List;

public interface Command {
    String getName();

    String getDescription();

    List<String> getAliases();

    void execute(CommandSender sender, List<String> args);

    List<String> tabComplete(CommandSender sender, List<String> args);
}
