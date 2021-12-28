package xyz.upperlevel.openverse.client.launcher;

import jline.console.ConsoleReader;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.server.OpenverseServer;

import java.io.IOException;
import java.util.List;

public class ConsoleListener {
    @Setter
    @Getter
    private OpenverseServer localServer;

    @Getter
    private final ConsoleReader in;

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
                if (line == null)
                    return;



                this.localServer.executeCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int onComplete(String line, int cursor, List<CharSequence> candidates) {
        return this.localServer.tabComplete(line, (List)candidates);
    }
}

