package xyz.upperlevel.openverse.server.command;

import xyz.upperlevel.openverse.Openverse;

public interface CommandSender {
    CommandSender CONSOLE = message -> Openverse.logger().info(message);

    void sendMessage(String message);
}
