package xyz.upperlevel.openverse.server.command.def;

import xyz.upperlevel.openverse.server.command.Command;
import xyz.upperlevel.openverse.server.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EchoCommand implements Command {
    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getDescription() {
        return "Echo";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("print", "repeat");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        sender.sendMessage(args.stream().collect(Collectors.joining(" ")));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, List<String> args) {
        return null;
    }
}
