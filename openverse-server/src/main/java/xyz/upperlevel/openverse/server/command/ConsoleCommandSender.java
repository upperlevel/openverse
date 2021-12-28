package xyz.upperlevel.openverse.server.command;

import lombok.Getter;

import java.util.logging.Logger;

public class ConsoleCommandSender implements CommandSender  {
    @Getter
    private final Logger logger;

    public ConsoleCommandSender(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void sendMessage(String message) {
        logger.info(message);
    }
}
