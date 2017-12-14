package xyz.upperlevel.openverse.server.command;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.command.def.EchoCommand;
import xyz.upperlevel.openverse.server.command.def.GiveCommand;
import xyz.upperlevel.openverse.server.command.def.HelpCommand;
import xyz.upperlevel.openverse.server.command.def.HotbarCommand;

import java.util.*;

public class CommandRegistry implements Iterable<Command> {
    private Set<Command> commands = new HashSet<>();
    private Map<String, Command> nameMappedCommands = new HashMap<>();

    public CommandRegistry() {
        registerDefaultCommands();
    }

    public boolean register(Command command) {
        if (!commands.add(command)) {
            return false;
        }
        if (nameMappedCommands.putIfAbsent(command.getName(), command) != null) {
            warn("Conflict with command by name '" + command.getName() + "' trying aliases");
        }
        for (String alias : command.getAliases()) {
            Command oldCommand = nameMappedCommands.putIfAbsent(alias, command);
            if (oldCommand != null) {
                warn("Conflict with alias '" + alias + "' for commands: '" + command.getName() + "' and '" + oldCommand + "'");
            }
        }
        return true;
    }

    public boolean unregister(Command command) {
        if (!commands.remove(command)) {
            return false;
        }

        nameMappedCommands.remove(command.getName(), command);
        for (String alias : command.getAliases()) {
            nameMappedCommands.remove(alias, command);
        }

        return true;
    }

    public Command get(String id) {
        return nameMappedCommands.get(id);
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }


    protected void registerDefaultCommands() {
        register(new EchoCommand());
        register(new HelpCommand(this));
        register(new HotbarCommand());
        register(new GiveCommand());
    }

    protected void warn(String str) {
        Openverse.logger().warning("[Command]" + str);
    }

    @Override
    public Iterator<Command> iterator() {
        return commands.iterator();
    }
}
