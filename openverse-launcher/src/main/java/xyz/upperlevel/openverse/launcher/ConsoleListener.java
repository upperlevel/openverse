package xyz.upperlevel.openverse.launcher;

import jline.console.ConsoleReader;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.launcher.loaders.ServerWrapper;

import java.io.IOException;
import java.util.List;

public class ConsoleListener {
    @Setter
    @Getter
    private ServerWrapper server;

    @Getter
    private ConsoleReader in;

    public ConsoleListener() {
        try {
            this.in = new ConsoleReader(System.in, System.out);
            in.setPrompt(">");
            in.addCompleter(this::onComplete);
            in.setExpandEvents(false);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                String line = in.readLine();
                if (line == null) return;
                server.executeCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int onComplete(String line, int cursor, List<CharSequence> candidates) {
        return server.tabComplete(line, (List)candidates);
    }
}
